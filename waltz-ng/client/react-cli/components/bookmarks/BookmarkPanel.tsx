import React, {useState, useMemo} from "react";
import BookmarkCategoryMenu from "./BookmarkCategoryMenu";
import SearchInput from "../common/SearchInput";
import Icon from "../common/Icon";
import {
  filterBookmarks,
  mkBookmarkKinds,
  nestBookmarks,
} from "../../../bookmarks/svelte/bookmark-utils"; // This path needs to be updated to the new TS version
import roles from "../../../user/system-roles";
import BookmarkRemovalConfirmation from "./BookmarkRemovalConfirmation";
import NoData from "../common/no-data/NoData";
import BookmarkEditor from "./BookmarkEditor";
import Loader from "../common/loader/Loader";
import Button from "../common/button/Button";
import BookmarkTable from "./BookmarkTable";
import {nestEnums} from "../../../common/svelte/enum-utils";
import {userManagementApi} from "../../api/user-management";
import {bookmarkApi} from "../../api/bookmark";
import {enumValueApi} from "../../api/enum-value";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {BookmarkGroup, BookmarkKinds, BookmarkType} from "../../types/Bookmark";
import {NotificationTypeEnum} from "../../enums/Notification";
import {EntityReference} from "../../types/Entity";
import {useToasts} from "../../context/toast/ToastContext";
import {Roles} from "../../enums/User";

type BookmarkPanelProps = {
  primaryEntityRef: EntityReference;
};

/**
 * BookmarkPanel component displays and manages bookmarks for a given entity.
 * It allows users to view, filter, search, add, edit, and remove bookmarks.
 */
const BookmarkPanel: React.FC<BookmarkPanelProps> = ({primaryEntityRef}) => {
  const queryClient = useQueryClient();
  const {addToast} = useToasts();
  const [selectedKind, setSelectedKind] = useState<BookmarkKinds | null>(null);
  const [query, setQuery] = useState("");
  const [removalCandidate, setRemovalCandidate] = useState<BookmarkType | null>(null);
  const [editCandidate, setEditCandidate] = useState<BookmarkType | null>(null);

  // Fetches bookmarks for the primary entity.
  const {
    data: bookmarks = [],
    isLoading: isLoadingBookmarks,
    error: bookmarksError,
  } = useQuery(bookmarkApi.load(primaryEntityRef));
  // Fetches current user data to check for permissions.
  const {data: user} = useQuery(userManagementApi.load());
  // Fetches all enum values, used for bookmark kinds.
  const {data: enums} = useQuery(enumValueApi.load());

  // Memoized calculation to nest enum values for efficient use.
  const nestedEnums = useMemo(() => nestEnums(enums), [enums]);
  // Memoized calculation to filter and group bookmarks based on kind and search query.
  const bookmarkGroups: BookmarkGroup[] = useMemo(
    () => nestBookmarks(nestedEnums, filterBookmarks(bookmarks, selectedKind, query)),
    [nestedEnums, bookmarks, selectedKind, query]
  );
  // Memoized calculation to derive available bookmark kinds from enums and existing bookmarks.
  const bookmarkKinds = useMemo(
    () => mkBookmarkKinds(nestedEnums, bookmarks),
    [nestedEnums, bookmarks]
  );

  // Memoized check to determine if the current user has editing permissions.
  const canEdit = useMemo(
    () => user?.roles?.includes(roles.BOOKMARK_EDITOR.key as Roles) ?? false,
    [user]
  );

  const editAction = {
    icon: "pencil",
    name: "Edit",
    handleAction: (d: BookmarkType) => setEditCandidate(d),
  };

  const removeAction = {
    icon: "trash",
    name: "Remove",
    handleAction: (d: BookmarkType) => setRemovalCandidate(d),
  };

  const actions = canEdit ? [editAction, removeAction] : [];

  // Handles selection of a bookmark kind from the category menu.
  const handleKindSelect = (kind: BookmarkKinds | null) => {
    setSelectedKind(kind);
  };

  // Initializes a new bookmark object for creation.
  const handleCreate = () => {
    setEditCandidate({
      bookmarkKind: selectedKind?.key ?? "DOCUMENTATION",
      parent: primaryEntityRef,
      lastUpdatedBy: "ignored, server will set",
    });
  };

  // Mutation for saving (creating or updating) a bookmark.
  const {mutate: saveMutation} = useMutation<number, Error, BookmarkType>({
    mutationFn: (bookmark: BookmarkType) => {
      const {mutationFn} = bookmarkApi.save(bookmark);
      return mutationFn();
    },
    onSuccess: () => {
      addToast({
        type: NotificationTypeEnum.SUCCESS,
        message: "Bookmark saved successfully",
      });
      setEditCandidate(null);
      queryClient.invalidateQueries({
        // Invalidates bookmark queries to refetch data.
        queryKey: ["bookmarks", "load", primaryEntityRef.kind, primaryEntityRef.id],
      });
    },
    onError: (error: any) => {
      const message = error.data?.message || error.message || "Could not save bookmark";
      addToast({type: NotificationTypeEnum.ERROR, message});
    },
  });

  // Mutation for removing a bookmark.
  const {mutate: removeMutation} = useMutation<number, Error, number>({
    mutationFn: (bookmarkId: number) => {
      const {mutationFn} = bookmarkApi.remove(bookmarkId);
      return mutationFn();
    },
    onSuccess: () => {
      addToast({
        type: NotificationTypeEnum.SUCCESS,
        message: "Bookmark removed successfully",
      });
      setRemovalCandidate(null);
      queryClient.invalidateQueries({
        // Invalidates bookmark queries to refetch data.
        queryKey: ["bookmarks", "load", primaryEntityRef.kind, primaryEntityRef.id],
      });
    },
    onError: (error: any) => {
      const message = error.data?.message || error.message || "Could not remove bookmark";
      addToast({type: NotificationTypeEnum.ERROR, message});
    },
  });

  const handleSave = (bookmark: BookmarkType) => saveMutation(bookmark);

  const handleRemove = () => {
    if (removalCandidate?.id) removeMutation(removalCandidate.id); // Ensures removal candidate and its ID exist.
  };

  if (isLoadingBookmarks) {
    return <Loader />;
  }

  if (bookmarksError) {
    return <div>Error loading bookmarks: {bookmarksError.message}</div>;
  }

  // Renders the main content area based on the current state.
  const renderContent = () => {
    if (removalCandidate) {
      return (
        <BookmarkRemovalConfirmation
          bookmark={removalCandidate}
          doRemove={handleRemove}
          doCancel={() => setRemovalCandidate(null)}
        />
      );
    }
    if (editCandidate) {
      return (
        <BookmarkEditor
          bookmark={editCandidate}
          kinds={bookmarkKinds}
          doSave={handleSave}
          doCancel={() => setEditCandidate(null)}
        />
      );
    }
    return (
      <>
        {bookmarks.length > 5 && (
          <>
            <div style={{display: "flex", justifyContent: "end", marginBottom: "6px"}}>
              <Button className="btn btn-success" style={{marginRight: "0px"}} onClick={handleCreate}>
                <Icon name="plus" /> Add bookmark
              </Button>
            </div>
            <SearchInput
              value={query}
              onChange={setQuery}
              placeholder="Search bookmarks..."
            />
            <br />
          </>
        )}
        {/* If there are no bookmarks, show a message and an add button if user can edit. */}
        {bookmarkGroups.length === 0 ? (
          <NoData>
            No bookmarks
            {canEdit && (
              <div style={{paddingTop: "1em"}}>
                <Button className="btn btn-sm btn-default" onClick={handleCreate}>
                  <Icon name="plus" /> Add bookmark
                </Button>
              </div>
            )}
          </NoData>
        ) : (
          <BookmarkTable bookmarkGroups={bookmarkGroups} actions={actions} />
        )}
      </>
    );
  };

  return (
    <div className="row row-mini-gutters">
      <div className="col-sm-3">
        <BookmarkCategoryMenu
          onKindSelect={handleKindSelect}
          bookmarkKinds={bookmarkKinds}
        />
      </div>
      <div className="col-sm-9">
        <div className="waltz-scroll-region-500">{renderContent()}</div>
      </div>
    </div>
  );
};

export default BookmarkPanel;
