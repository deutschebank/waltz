import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import BookmarkListItem from "./BookmarkListItem";
import { IBookmark } from "../../types/Bookmark";

jest.mock("../common/LastEdited", () => () => <span>Last Edited Mock</span>);
jest.mock("../common/mini-actions/MiniActions", () => () => <span>Mini Actions Mock</span>);

describe("BookmarkListItem", () => {
  const mockBookmark: IBookmark = {
      id: 1,
      title: "Waltz Home",
      url: "https://waltz.example.com",
      domain: "waltz.example.com",
      description: "This is a test bookmark.",
      isRestricted: true,
      icon: "link",
      bookmarkKind: "",
      parent: {
          description: "",
          kind: "ACTOR",
          id: 0,
          name: "",
          externalId: "",
          entityLifecycleStatus: "UNKNOWN"
      }
  };

  it("renders bookmark details correctly", () => {
    render(
      <table>
        <tbody>
          <BookmarkListItem bookmark={mockBookmark} />
        </tbody>
      </table>
    );

    expect(screen.getByText("Waltz Home")).toBeInTheDocument();
    expect(screen.getByText("(waltz.example.com)")).toBeInTheDocument();
    expect(screen.getByText("This is a test bookmark.")).toBeInTheDocument();
    expect(screen.getByText(/Permissions may be required/)).toBeInTheDocument();
  });

  it("shows a lock icon for restricted bookmarks", () => {
    render(<table><tbody><BookmarkListItem bookmark={mockBookmark}/></tbody></table>);
    const lockIcon = screen.getByText(/Permissions may be required/).querySelector(".icon");
    expect(lockIcon).toHaveAttribute("data-ux", "lock");
  });
});