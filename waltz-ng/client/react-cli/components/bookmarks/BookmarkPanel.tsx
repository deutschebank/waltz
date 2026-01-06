import React, {useState, useMemo} from "react";
import _ from "lodash";
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
import {BookmarkKinds, BookmarkType} from "../../types/Bookmark";
import {NotificationTypeEnum} from "../../enums/Notification";
import {EntityReference} from "../../types/Entity";
import {useToasts} from "../../context/toast/ToastContext";

// Defines the props for the BookmarkPanel component.
type BookmarkPanelProps = {
  primaryEntityRef: EntityReference;
};

/**
 * BookmarkPanel component displays and manages bookmarks for a given entity.
 * It allows users to view, filter, search, add, edit, and remove bookmarks.
 */
const BookmarkPanel: React.FC<BookmarkPanelProps> = ({primaryEntityRef}) => {
  // React Query client for cache invalidation.
  const queryClient = useQueryClient();
  // Custom hook for displaying toast notifications.
  const {addToast} = useToasts();

  // State for the selected bookmark kind/category.
  const [selectedKind, setSelectedKind] = useState<BookmarkKinds | null>(null);
  // State for the search query string.
  const [query, setQuery] = useState("");
  // State to hold the bookmark being considered for removal.
  const [removalCandidate, setRemovalCandidate] = useState<BookmarkType | null>(null);
  // State to hold the bookmark being edited or created.
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
  const bookmarkGroups = useMemo(
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
    () => _.includes(user?.roles, roles.BOOKMARK_EDITOR.key),
    [user]
  );

  // Action definition for editing a bookmark.
  const editAction = {
    icon: "pencil",
    name: "Edit",
    handleAction: (d: BookmarkType) => setEditCandidate(d),
  };

  // Action definition for removing a bookmark.
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

  // Handler to trigger the save mutation.
  const handleSave = (bookmark: BookmarkType) => saveMutation(bookmark);

  // Handler to trigger the remove mutation.
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
    // If a bookmark is marked for removal, show the confirmation dialog.
    if (removalCandidate) {
      return (
        <BookmarkRemovalConfirmation
          bookmark={removalCandidate}
          doRemove={handleRemove}
          doCancel={() => setRemovalCandidate(null)}
        />
      );
    }
    // If a bookmark is being edited or created, show the editor form.
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
    // Default view: shows search, add button, and the list of bookmarks.
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
        {_.isEmpty(bookmarkGroups) ? (
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
          // Otherwise, display the bookmarks in a table.
          <BookmarkTable bookmarkGroups={bookmarkGroups} actions={actions} />
        )}
      </>
    );
  };

  return (
    // Main layout with two columns.
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
