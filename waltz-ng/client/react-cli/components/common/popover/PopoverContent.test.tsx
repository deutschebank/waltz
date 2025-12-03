import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import PopoverContent from "./PopoverContent";

// Mock the Icon component since it's a dependency and not the focus of this unit test.
jest.mock("../Icon", () => () => <i data-testid="icon-mock" />);

describe("PopoverContent", () => {
    it("should render its children correctly", () => {
        render(
            <PopoverContent>
                <p>This is the test content</p>
            </PopoverContent>
        );
        expect(
            screen.getByText("This is the test content")
        ).toBeInTheDocument();
    });

    it("should display the dismiss button by default", () => {
        render(<PopoverContent>Content</PopoverContent>);
        // The close button is identified by its aria-label.
        expect(screen.getByLabelText("Close")).toBeInTheDocument();
    });

    it("should call the onDismiss function when the close button is clicked", () => {
        const handleDismiss = jest.fn();
        render(
            <PopoverContent onDismiss={handleDismiss}>Content</PopoverContent>
        );

        const closeButton = screen.getByLabelText("Close");
        fireEvent.click(closeButton);

        expect(handleDismiss).toHaveBeenCalledTimes(1);
    });

    it("should not display the dismiss button when dismissible prop is false", () => {
        render(<PopoverContent dismissible={false}>Content</PopoverContent>);
        expect(screen.queryByLabelText("Close")).not.toBeInTheDocument();
    });

    it("should render without issues when dismissible is false and onDismiss is not provided", () => {
        const { container } = render(
            <PopoverContent dismissible={false}>Content</PopoverContent>
        );
        expect(container).toBeInTheDocument();
    });
});