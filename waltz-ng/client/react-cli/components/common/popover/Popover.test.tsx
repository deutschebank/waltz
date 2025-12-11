import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import Popover from "./Popover";
import { useSliceSelector } from "../../../hooks/useSliceSelector";
import reduxStore from "../../../../redux-store";
import { dismissPopover } from "../../../../redux-slices/popover-slice";

// Mock dependencies
jest.mock("../../../hooks/useSliceSelector");
jest.mock("../../../../redux-store", () => ({
    dispatch: jest.fn(),
}));
jest.mock("../../../../redux-slices/popover-slice", () => ({
    dismissPopover: jest.fn(),
}));

// Mock PopoverContent to simplify testing Popover in isolation.
jest.mock(
    "./PopoverContent",
    () =>
        ({
            children,
            onDismiss,
        }: {
            children: React.ReactNode;
            onDismiss: () => void;
        }) =>
            (
                <div>
                    <button onClick={onDismiss}>Dismiss</button>
                    {children}
                </div>
            )
);

const useSliceSelectorMock = useSliceSelector as jest.Mock;
const reduxStoreMock = reduxStore as jest.Mocked<typeof reduxStore>;

describe("Popover", () => {
    beforeEach(() => {
        // Clear mock history before each test
        jest.clearAllMocks();
    });

    it("should render nothing when no popover is active", () => {
        useSliceSelectorMock.mockReturnValue(null);
        const { container } = render(<Popover />);
        expect(container).toBeEmptyDOMElement();
    });

    it("should render a popover with a title and HTML content", () => {
        const popoverData = {
            title: "Test Title",
            content: "<p>Test Content</p>",
        };
        useSliceSelectorMock.mockReturnValue(popoverData);

        render(<Popover />);

        expect(screen.getByText("Test Title")).toBeInTheDocument();
        expect(screen.getByText("Test Content")).toBeInTheDocument();
        expect(screen.getByLabelText("Close Popover")).toBeInTheDocument();
    });

    it("should render a popover with a dynamic component", () => {
        const DynamicComponent = ({ text }: { text: string }) => (
            <div>{text}</div>
        );
        const popoverData = {
            title: "Component Popover",
            component: DynamicComponent,
            props: { text: "Hello from component" },
        };
        useSliceSelectorMock.mockReturnValue(popoverData);

        render(<Popover />);

        expect(screen.getByText("Component Popover")).toBeInTheDocument();
        expect(screen.getByText("Hello from component")).toBeInTheDocument();
    });

    it("should dispatch dismissPopover when backdrop is clicked", () => {
        const popoverData = { title: "Test" };
        useSliceSelectorMock.mockReturnValue(popoverData);

        render(<Popover />);

        fireEvent.click(screen.getByLabelText("Close Popover"));

        expect(reduxStoreMock.dispatch).toHaveBeenCalledTimes(1);
        expect(reduxStoreMock.dispatch).toHaveBeenCalledWith(dismissPopover());
    });

    it("should dispatch dismissPopover when Escape key is pressed", () => {
        const popoverData = { title: "Test" };
        useSliceSelectorMock.mockReturnValue(popoverData);

        render(<Popover />);

        fireEvent.keyDown(window, { key: "Escape", code: "Escape" });

        expect(reduxStoreMock.dispatch).toHaveBeenCalledTimes(1);
        expect(reduxStoreMock.dispatch).toHaveBeenCalledWith(dismissPopover());
    });

    it("should not dispatch dismissPopover for other key presses", () => {
        const popoverData = { title: "Test" };
        useSliceSelectorMock.mockReturnValue(popoverData);

        render(<Popover />);

        fireEvent.keyDown(window, { key: "Enter", code: "Enter" });

        expect(reduxStoreMock.dispatch).not.toHaveBeenCalled();
    });

    it("should dispatch dismissPopover when dismiss is triggered from PopoverContent", () => {
        const popoverData = { title: "Test" };
        useSliceSelectorMock.mockReturnValue(popoverData);

        render(<Popover />);

        // The mock PopoverContent has a "Dismiss" button
        fireEvent.click(screen.getByText("Dismiss"));

        expect(reduxStoreMock.dispatch).toHaveBeenCalledTimes(1);
        expect(reduxStoreMock.dispatch).toHaveBeenCalledWith(dismissPopover());
    });

    it("should clean up keydown event listener on unmount", () => {
        const popoverData = { title: "Test" };
        useSliceSelectorMock.mockReturnValue(popoverData);
        const removeEventListenerSpy = jest.spyOn(
            window,
            "removeEventListener"
        );

        const { unmount } = render(<Popover />);
        unmount();

        expect(removeEventListenerSpy).toHaveBeenCalledWith(
            "keydown",
            expect.any(Function)
        );
    });
});