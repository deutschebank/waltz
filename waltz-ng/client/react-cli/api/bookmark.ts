import {fetchJSON, execute} from "./api";
import {bookmarkPath} from "../constants/path";
import {BookmarkType, BookmarkBasic} from "../types/Bookmark";
import _ from "lodash";
import {EntityReference} from "../types/Entity";
import {toEntityRef} from "../../common/entity-utils";

function stripExtraneousFields(bookmark: BookmarkType): BookmarkBasic {
  const simplifiedBookmark = _.pick(bookmark, [
    "id",
    "bookmarkKind",
    "url",
    "title",
    "isRestricted",
    "description",
    "lastUpdatedBy",
  ]);

  const simplifiedParent = {parent: toEntityRef(bookmark.parent)};

  return Object.assign({}, simplifiedBookmark, simplifiedParent) as BookmarkBasic;
}

const load = (ref: EntityReference) => ({
  queryKey: ["bookmarks", "load", ref.kind, ref.id],
  queryFn: async (): Promise<BookmarkType[]> => {
    return await fetchJSON(bookmarkPath.load(ref));
  },
  enabled: !!ref,
});

const save = (bookmark: BookmarkType) => ({
  mutationKey: ["bookmarks", "save", bookmark.id],
  mutationFn: async (): Promise<number> => {
    return await execute(bookmarkPath.save(), "POST", stripExtraneousFields(bookmark));
  },
});

const remove = (bookmarkId: number) => ({
  mutationKey: ["bookmarks", "remove", bookmarkId],
  mutationFn: async (): Promise<number> => {
    return await execute(bookmarkPath.remove(bookmarkId), "DELETE");
  },
});

export const bookmarkApi = {
  load,
  save,
  remove,
};
