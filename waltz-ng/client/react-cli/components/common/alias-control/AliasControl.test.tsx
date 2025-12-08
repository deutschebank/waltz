import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import AliasControl from "./AliasControl";
import { useQuery, useQueryClient, useMutation } from "@tanstack/react-query";
import { mkRef } from "../../../utils/mkRef";
import "@testing-library/jest-dom";
import { useToasts } from "../../../context/toast/ToastContext";

// Mocking React Query
jest.mock("@tanstack/react-query", () => ({
    ...jest.requireActual("@tanstack/react-query"),
    useQuery: jest.fn(),
    useQueryClient: jest.fn(),
    useMutation: jest.fn(),
}));

jest.mock("../../../context/ToastContext", () => ({
    useToasts: jest.fn(),
}));

// Mock parentEntityReference
const mockParentEntityReference = mkRef({
    kind: "ACTOR",
    id: 1206,
});

// Mock TagInput component
jest.mock("../tags-input/TagsInput", () => {
    return jest.fn(({ value, list, onSave, onCancel }: any) => (
        <div>
            <div data-testid="tag-input">
                {value.map((v: string, index: number) => (
                    <span key={index}>{v}</span>
                ))}
            </div>
            <button
                data-testid="save-button"
                onClick={() => onSave(["alias1", "alias2"])}
            >
                Save
            </button>
            <button data-testid="cancel-button" onClick={onCancel}>
                Cancel
            </button>
        </div>
    ));
});

describe("AliasControl", () => {
    const aliases = ["Alias1", "Alias2"];
    const mockAddToast = jest.fn();
    const mockQueryClient = {
        getQueryData: jest.fn(),
        setQueryData: jest.fn(),
    };

    beforeEach(() => {
        // Mock the "useQuery" behavior
        (useQuery as jest.Mock).mockReturnValue({
            isPending: false,
            data: aliases,
        });

        (useToasts as jest.Mock).mockReturnValue({
            addToast: mockAddToast,
        });

        // Mock the "useQueryClient" behavior
        (useQueryClient as jest.Mock).mockReturnValue(mockQueryClient);

        // Mock the "useMutation" behavior
        (useMutation as jest.Mock).mockImplementation(() => ({
            mutate: jest.fn(),
        }));
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test("renders alias list in VIEW mode", () => {
        render(
            <AliasControl parentEntityReference={mockParentEntityReference} />
        );

        // Ensure alias list renders
        aliases.forEach((alias) => {
            expect(screen.getByText(alias)).toBeInTheDocument();
        });

        // Ensure the "Edit" button does not render by default
        expect(
            screen.queryByRole("button", { name: /edit/i })
        ).not.toBeInTheDocument();
    });

    test("renders 'No aliases defined' if no aliases exist", () => {
        // Mock empty aliases
        (useQuery as jest.Mock).mockReturnValueOnce({
            isPending: false,
            data: [],
        });

        render(
            <AliasControl parentEntityReference={mockParentEntityReference} />
        );

        // Ensure "No aliases defined" message is displayed
        expect(screen.getByTestId("no-aliases")).toBeInTheDocument();
    });

    test("renders the edit button when editable is true", () => {
        render(
            <AliasControl
                parentEntityReference={mockParentEntityReference}
                editable
            />
        );

        // Check for the Edit button
        const editButton = screen.getByRole("button", { name: /edit/i });
        expect(editButton).toBeInTheDocument();
    });

    test("toggles to EDIT mode when edit button is clicked", () => {
        render(
            <AliasControl
                parentEntityReference={mockParentEntityReference}
                editable
            />
        );

        const editButton = screen.getByRole("button", { name: /edit/i });

        // Click the Edit button
        fireEvent.click(editButton);

        // Ensure TagInput is now rendered (verify using its test id)
        expect(screen.getByTestId("tag-input")).toBeInTheDocument();
    });

    test("saves new aliases via mutation on save button click", async () => {
        const mockMutate = jest.fn();
        (useMutation as jest.Mock).mockImplementation(() => ({
            mutate: mockMutate,
        }));

        render(
            <AliasControl
                parentEntityReference={mockParentEntityReference}
                editable
            />
        );

        // Switch to EDIT mode
        const editButton = screen.getByRole("button", { name: /edit/i });
        fireEvent.click(editButton);

        // Click Save button (provided by TagInput mock)
        const saveButton = screen.getByTestId("save-button");
        fireEvent.click(saveButton);

        // Check the mutation function was called with new aliases
        expect(mockMutate).toHaveBeenCalledTimes(1);
        expect(mockMutate).toHaveBeenCalledWith(["alias1", "alias2"]);
    });

    test("cancels editing when Cancel button is clicked", () => {
        render(
            <AliasControl
                parentEntityReference={mockParentEntityReference}
                editable
            />
        );

        // Switch to EDIT mode
        const editButton = screen.getByRole("button", { name: /edit/i });
        fireEvent.click(editButton);

        // Click Cancel button (provided by TagInput mock)
        const cancelButton = screen.getByTestId("cancel-button");
        fireEvent.click(cancelButton);

        // Confirm mode has been switched back to VIEW
        expect(screen.queryByTestId("tag-input")).not.toBeInTheDocument();
        aliases.forEach((alias) => {
            expect(screen.getByText(alias)).toBeInTheDocument();
        });
    });
});
