import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useToasts } from "../../context/ToastContext";
import { NotificationTypeEnum } from "../../enums/Notification";
import {
    setActiveMode,
    setSelectedUser,
    setUserRoles,
} from "../../../redux-slices/user-management-slice";
import { userManagementApi } from "../../api/user-management";
import reduxStore from "../../../redux-store";
import { Modes } from "../../enums/User";
import { useSliceSelector } from "../../hooks/useSliceSelector";
import DeleteUser from "./DeleteUser";

// Mock external modules
jest.mock("@tanstack/react-query", () => ({
    useMutation: jest.fn(),
    useQueryClient: jest.fn(),
}));

jest.mock("../../context/ToastContext", () => ({
    useToasts: jest.fn(),
}));

jest.mock("../../../redux-store", () => ({
    dispatch: jest.fn(),
}));

jest.mock("../../api/user-management", () => ({
    userManagementApi: {
        deleteUser: jest.fn()
    },
}));

jest.mock("../../hooks/useSliceSelector", () => ({
    useSliceSelector: jest.fn(),
}));

describe("DeleteUser", () => {
    const mockAddToast = jest.fn();
    const mockMutate = jest.fn();
    const mockInvalidateQueries = jest.fn();
    const mockDeleteUserApi = userManagementApi.deleteUser as jest.Mock;

    beforeEach(() => {
        jest.clearAllMocks();

        (useToasts as jest.Mock).mockReturnValue({
            addToast: mockAddToast,
        });
        (useMutation as jest.Mock).mockReturnValue({
            mutate: mockMutate,
            isLoading: false,
            isError: false,
            isSuccess: false,
        });
        (useQueryClient as jest.Mock).mockReturnValue({
            invalidateQueries: mockInvalidateQueries,
        });

        // Default mock for useSliceSelector to return a selected user
        (useSliceSelector as jest.Mock).mockReturnValue({
            userName: "testUser",
            userId: "testUser",
            roles: ["ADMIN"],
        });

        mockDeleteUserApi.mockReturnValue({
            mutationFn: jest.fn(() =>
                Promise.resolve({
                    ok: true,
                })
            ),
        });
    });

    // Test Case 1: Renders confirmation message when a user is selected
    test("renders confirmation message when a user is selected", () => {
        render(<DeleteUser />);

        expect(
            screen.getByText(/Are you sure you want to delete user: testUser\?/)
        ).toBeInTheDocument();
        expect(
            screen.getByRole("button", {
                name: "Delete",
            })
        ).toBeInTheDocument();
        expect(
            screen.getByRole("button", {
                name: "Cancel",
            })
        ).toBeInTheDocument();
    });

    // Test Case 2: Renders "No user selected" message when selectedUser is null
    test("renders 'No user selected' message when selectedUser is null", () => {
        (useSliceSelector as jest.Mock).mockReturnValue(null);
        render(<DeleteUser />);

        expect(screen.getByText("No user selected")).toBeInTheDocument();
        expect(
            screen.queryByRole("button", {
                name: "Delete",
            })
        ).not.toBeInTheDocument();
    });

    // Test Case 3: Calls deleteUserMutation with correct username on Delete button click
    test("calls deleteUserMutation with correct username on Delete button click", () => {
        const { mutate } = useMutation({
            mutationFn: (username: string) => {
                const { mutationFn } = userManagementApi.deleteUser(username);
                return mutationFn();
            },
        });
        render(<DeleteUser />);

        fireEvent.click(
            screen.getByRole("button", {
                name: "Delete",
            })
        );

        expect(mutate).toHaveBeenCalledWith("testUser");
    });

    // Test Case 4: Handles successful user deletion
    test("handles successful user deletion", async () => {
        (useMutation as jest.Mock).mockImplementation((options) => ({
            mutate: (username: string) => {
                options.onSuccess(true, username, undefined);
            },
            isLoading: false,
            isError: false,
            isSuccess: false,
        }));

        render(<DeleteUser />);

        fireEvent.click(
            screen.getByRole("button", {
                name: "Delete",
            })
        );

        await waitFor(() => {
            expect(mockAddToast).toHaveBeenCalledWith({
                type: NotificationTypeEnum.SUCCESS,
                message: "Successfully deleted user: testUser",
            });
            expect(reduxStore.dispatch).toHaveBeenCalledWith(
                setSelectedUser(null)
            );
            expect(reduxStore.dispatch).toHaveBeenCalledWith(setUserRoles([]));
            expect(reduxStore.dispatch).toHaveBeenCalledWith(
                setActiveMode(Modes.LIST)
            );
            expect(mockInvalidateQueries).toHaveBeenCalledWith({
                queryKey: ["user", "findAll"],
            });
        });
    });

    // Test Case 5: Handles failed user deletion (API returns !response.ok)
    test("handles failed user deletion (API returns !response.ok)", async () => {
        (useMutation as jest.Mock).mockImplementation((options) => ({
            mutate: (username: string) => {
                options.onError(
                    {
                        data: { message: "User not found" },
                    },
                    username,
                    undefined
                );
            },
            isLoading: false,
            isError: false,
            isSuccess: false,
        }));

        render(<DeleteUser />);

        fireEvent.click(
            screen.getByRole("button", {
                name: "Delete",
            })
        );

        await waitFor(() => {
            expect(mockAddToast).toHaveBeenCalledWith({
                type: NotificationTypeEnum.ERROR,
                message: "Failed to delete user: User not found",
            });
        });
    });

    // Test Case 6: Handles failed user deletion (network error or other exception)
    test("handles failed user deletion (network error or other exception)", async () => {
        (useMutation as jest.Mock).mockImplementation((options) => ({
            mutate: (username: string) => {
                options.onError(
                    {
                        message: "Network Error",
                    },
                    username,
                    undefined
                );
            },
            isLoading: false,
            isError: false,
            isSuccess: false,
        }));

        render(<DeleteUser />);

        fireEvent.click(
            screen.getByRole("button", {
                name: "Delete",
            })
        );

        await waitFor(() => {
            expect(mockAddToast).toHaveBeenCalledWith({
                type: NotificationTypeEnum.ERROR,
                message: "Failed to delete user: Network Error",
            });
        });
    });

    // Test Case 7: Handles Cancel button click
    test("handles Cancel button click", () => {
        render(<DeleteUser />);

        fireEvent.click(
            screen.getByRole("button", {
                name: "Cancel",
            })
        );

        expect(reduxStore.dispatch).toHaveBeenCalledWith(
            setActiveMode(Modes.DETAIL)
        );
    });
});
