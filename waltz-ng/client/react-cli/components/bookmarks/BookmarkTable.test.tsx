import React from "react";
import {render, screen} from "@testing-library/react";
import "@testing-library/jest-dom";
import BookmarkTable from "./BookmarkTable";
import {BookmarkGroup, BookmarkType} from "../../types/Bookmark";
import {EntityKind, EntityLifecycleStatus} from "../../enums/Entity";

jest.mock("./BookmarkListItem", () => ({bookmark}: {bookmark: BookmarkType}) => (
  <tr>
    <td>{bookmark.title}</td>
  </tr>
));

const baseBookmark: BookmarkType = {
  id: 0,
  bookmarkKind: "DOCUMENTATION",
  parent: {
    kind: EntityKind.APPLICATION,
    id: 1,
    name: "test",
    description: "test",
    externalId: "test",
    entityLifecycleStatus: EntityLifecycleStatus.ACTIVE,
  },
};

describe("BookmarkTable", () => {
  const mockBookmarkGroups: BookmarkGroup[] = [
    {
      key: "DOCUMENTATION",
      value: [
        {...baseBookmark, id: 1, title: "Doc Bookmark 1"},
        {...baseBookmark, id: 2, title: "Doc Bookmark 2"},
      ],
    },
    {
      key: "REPORTING", // Assuming REPORTING is also a valid BookmarkKind
      value: [{...baseBookmark, id: 3, title: "Report Bookmark 1"}],
    },
  ];

  it("renders a table with rows for each bookmark in the groups", () => {
    render(<BookmarkTable bookmarkGroups={mockBookmarkGroups} actions={[]} />);

    expect(screen.getByText("Doc Bookmark 1")).toBeInTheDocument();
    expect(screen.getByText("Doc Bookmark 2")).toBeInTheDocument();
    expect(screen.getByText("Report Bookmark 1")).toBeInTheDocument();
    expect(screen.getAllByRole("row").length).toBe(3);
  });
});
