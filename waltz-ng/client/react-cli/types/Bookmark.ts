import { EntityReference } from "./Entity";

export interface IBookmark {
  id?: number;
  bookmarkKind: string;
  url: string;
  title: string;
  isRestricted: boolean;
  parent: EntityReference;
  description?: string;
  lastUpdatedBy?: string;
  lastUpdatedAt?: string;
  domain?: string;
  icon?: string;
}

export interface IBookmarkBasic extends Omit<IBookmark, "parent"> {
  parent: EntityReference;
}
