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
  key?: string;
}

// This type is used for the `bookmarkKinds` array, which contains metadata about each kind.
export interface BookmarkKinds {
  key: string;
  name: string;
  icon: string;
  count: number;
}; 

export interface BookmarkBasic extends Omit<BookmarkType, "parent"> {
  parent: EntityReference;
}
