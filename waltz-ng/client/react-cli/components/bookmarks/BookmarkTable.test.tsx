import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import BookmarkTable from "./BookmarkTable";

jest.mock("./BookmarkListItem", () => ({ bookmark }: { bookmark: any }) => (
  <tr>
    <td>{bookmark.title}</td>
  </tr>
));

describe("BookmarkTable", () => {
  const mockBookmarkGroups = [
    {
      key: "DOCUMENTATION",
      value: [
        { id: 1, title: "Doc Bookmark 1" },
        { id: 2, title: "Doc Bookmark 2" },
      ],
    },
    {
      key: "REPORTING",
      value: [{ id: 3, title: "Report Bookmark 1" }],
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