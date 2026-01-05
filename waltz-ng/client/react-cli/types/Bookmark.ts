import { EntityReference } from "./Entity";

export interface BookmarkType {
  id?: number;
  bookmarkKind: string;
  url?: string;
  title?: string;
  isRestricted?: boolean;
  parent: EntityReference;
  description?: string;
  lastUpdatedBy?: string;
  lastUpdatedAt?: string;
  domain?: string;
  icon?: string;
}

export interface BookmarkBasic extends Omit<BookmarkType, "parent"> {
  parent: EntityReference;
}
