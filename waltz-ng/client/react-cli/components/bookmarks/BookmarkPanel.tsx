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
import {IBookmark} from "../../types/Bookmark";
import {useToasts} from "../../context/ToastContext";
import {NotificationTypeEnum} from "../../enums/Notification";

interface BookmarkPanelProps {
  primaryEntityRef: any; // Define a proper type for entity reference
}

const BookmarkPanel: React.FC<BookmarkPanelProps> = ({primaryEntityRef}) => {
  const queryClient = useQueryClient();
  const {addToast} = useToasts();

  const [selectedKind, setSelectedKind] = useState<any | null>(null);
  const [query, setQuery] = useState("");
  const [removalCandidate, setRemovalCandidate] = useState<IBookmark | null>(null);
  const [editCandidate, setEditCandidate] = useState<IBookmark | null>(null);

  const {
    data: bookmarks = [],
    isLoading: isLoadingBookmarks,
    error: bookmarksError,
  } = useQuery(bookmarkApi.load(primaryEntityRef));
  const {data: user} = useQuery(userManagementApi.load());
  const {data: enums} = useQuery(enumValueApi.load());

  const nestedEnums = useMemo(() => nestEnums(enums), [enums]);
  const bookmarkGroups = useMemo(
    () => nestBookmarks(nestedEnums, filterBookmarks(bookmarks, selectedKind, query)),
    [nestedEnums, bookmarks, selectedKind, query]
  );
  const bookmarkKinds = useMemo(
    () => mkBookmarkKinds(nestedEnums, bookmarks),
    [nestedEnums, bookmarks]
  );

  const canEdit = useMemo(
    () => _.includes(user?.roles, roles.BOOKMARK_EDITOR.key),
    [user]
  );

  const editAction = {
    icon: "pencil",
    name: "Edit",
    handleAction: (d: any) => setEditCandidate(d),
  };

  const removeAction = {
    icon: "trash",
    name: "Remove",
    handleAction: (d: any) => setRemovalCandidate(d),
  };

  const actions = canEdit ? [editAction, removeAction] : [];

  const handleKindSelect = (kind: any) => {
    setSelectedKind(kind);
  };

  const handleCreate = () => {
    setEditCandidate({
      bookmarkKind: _.get(selectedKind, "key", "DOCUMENTATION"),
      parent: primaryEntityRef,
      lastUpdatedBy: "ignored, server will set",
    });
  };

  const {mutate: saveMutation} = useMutation<number, Error, IBookmark>({
    mutationFn: (bookmark: IBookmark) => {
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
        queryKey: ["bookmarks", "load", primaryEntityRef.kind, primaryEntityRef.id],
      });
    },
    onError: (error: any) => {
      const message = error.data?.message || error.message || "Could not save bookmark";
      addToast({type: NotificationTypeEnum.ERROR, message});
    },
  });

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
        queryKey: ["bookmarks", "load", primaryEntityRef.kind, primaryEntityRef.id],
      });
    },
    onError: (error: any) => {
      const message = error.data?.message || error.message || "Could not remove bookmark";
      addToast({type: NotificationTypeEnum.ERROR, message});
    },
  });

  const handleSave = (bookmark: IBookmark) => saveMutation(bookmark);

  const handleRemove = () => {
    if (removalCandidate?.id) removeMutation(removalCandidate.id);
  };

  if (isLoadingBookmarks) {
    return <Loader />;
  }

  if (bookmarksError) {
    return <div>Error loading bookmarks: {bookmarksError.message}</div>;
  }

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
            <div style={{display: "flex", justifyContent: "end"}}>
              <Button className="btn btn-success" onClick={handleCreate}>
                <Icon name="plus" /> Add bookmark
              </Button>
            </div>
            <br />
            <SearchInput
              value={query}
              onChange={setQuery}
              placeholder="Search bookmarks..."
            />
            <br />
          </>
        )}
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
          <BookmarkTable bookmarkGroups={bookmarkGroups} actions={actions}>
            <tfoot>
              {canEdit && (
                <tr>
                  <td colSpan={3}>
                    <Button className="btn btn-link" onClick={handleCreate}>
                      <Icon name="plus" /> Add bookmark
                    </Button>
                  </td>
                </tr>
              )}
            </tfoot>
          </BookmarkTable>
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
