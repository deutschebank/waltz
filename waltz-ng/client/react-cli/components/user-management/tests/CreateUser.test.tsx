import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { useMutation } from "@tanstack/react-query";
import { NotificationTypeEnum } from "../../../enums/Notification";
import {
    setSelectedUser,
    setUserRoles,
    setActiveMode,
} from "../../../../redux-slices/user-management-slice";
import { userManagementApi } from "../../../api/user-management";
import reduxStore from "../../../../redux-store";
import CreateUser from "../CreateUser";
import { UserRoles } from "../../../enums/User";
import { VisualStateModes } from "../../../enums/VisualState";
import { useToasts } from "../../../context/toast/ToastContext";

// Mock external modules
jest.mock("@tanstack/react-query", () => ({
    useMutation: jest.fn(),
}));

jest.mock("../../../context/toast/ToastContext", () => ({
    useToasts: jest.fn(),
}));

jest.mock("../../../../redux-store", () => ({
    dispatch: jest.fn(),
}));

jest.mock("../../../api/user-management", () => ({
    userManagementApi: {
        register: jest.fn(),
        getByUserId: jest.fn(),
    },
}));

describe("CreateUser", () => {
    const mockAddToast = jest.fn();
    const mockMutate = jest.fn();
    const mockRegister = userManagementApi.register as jest.Mock;
    const mockGetByUserId = userManagementApi.getByUserId as jest.Mock;

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

        mockRegister.mockReturnValue({
            mutationFn: jest.fn(() =>
                Promise.resolve({
                    ok: true,
                })
            ),
        });
        mockGetByUserId.mockReturnValue({
            queryFn: jest.fn(() =>
                Promise.resolve({
                    userId: "testUser",
                    roles: ["ADMIN"],
                    name: "testUser",
                    userName: "testUser",
                })
            ),
        });
    });

    // Test Case 1: Renders correctly with initial state
    test("renders correctly with initial state", () => {
        render(<CreateUser />);

        expect(screen.getByText("New User Registration")).toBeInTheDocument();
        expect(screen.getByLabelText("User Name")).toHaveValue("");
        expect(screen.getByLabelText("Password")).toHaveValue("");
        expect(
            screen.getByRole("button", {
                name: "Register",
            })
        ).toBeDisabled();
        expect(
            screen.getByRole("button", {
                name: "Cancel",
            })
        ).toBeInTheDocument();
    });

    // Test Case 2: Updates username and password on input change
    test("updates username and password on input change", () => {
        render(<CreateUser />);

        const usernameInput = screen.getByLabelText("User Name");
        const passwordInput = screen.getByLabelText("Password");

        fireEvent.change(usernameInput, {
            target: {
                value: "testUser",
            },
        });
        fireEvent.change(passwordInput, {
            target: {
                value: "testPassword",
            },
        });

        expect(usernameInput).toHaveValue("testUser");
        expect(passwordInput).toHaveValue("testPassword");
    });

    // Test Case 3: Enables register button when both fields are filled
    test("enables register button when both fields are filled", () => {
        render(<CreateUser />);

        const usernameInput = screen.getByLabelText("User Name");
        const passwordInput = screen.getByLabelText("Password");
        const registerButton = screen.getByRole("button", {
            name: "Register",
        });

        fireEvent.change(usernameInput, {
            target: {
                value: "testUser",
            },
        });
        fireEvent.change(passwordInput, {
            target: {
                value: "testPassword",
            },
        });

        expect(registerButton).toBeEnabled();
    });

    // Test Case 4: Disables register button when username is empty
    test("disables register button when username is empty", () => {
        render(<CreateUser />);

        const usernameInput = screen.getByLabelText("User Name");
        const passwordInput = screen.getByLabelText("Password");
        const registerButton = screen.getByRole("button", {
            name: "Register",
        });

        fireEvent.change(usernameInput, {
            target: {
                value: "",
            },
        });
        fireEvent.change(passwordInput, {
            target: {
                value: "testPassword",
            },
        });

        expect(registerButton).toBeDisabled();
    });

    // Test Case 5: Disables register button when password is empty
    test("disables register button when password is empty", () => {
        render(<CreateUser />);

        const usernameInput = screen.getByLabelText("User Name");
        const passwordInput = screen.getByLabelText("Password");
        const registerButton = screen.getByRole("button", {
            name: "Register",
        });

        fireEvent.change(usernameInput, {
            target: {
                value: "testUser",
            },
        });
        fireEvent.change(passwordInput, {
            target: {
                value: "",
            },
        });

        expect(registerButton).toBeDisabled();
    });

    // Test Case 6: Calls registerMutation.mutate with correct data on register button click
    test("calls registerMutation.mutate with correct data on register button click", async () => {
        const mockOnSuccess = jest.fn();
        (useMutation as jest.Mock).mockReturnValue({
            mutate: mockMutate,
            isLoading: false,
            isError: false,
            isSuccess: false,
            onSuccess: mockOnSuccess,
        });

        render(<CreateUser />);

        const usernameInput = screen.getByLabelText("User Name");
        const passwordInput = screen.getByLabelText("Password");
        const registerButton = screen.getByRole("button", {
            name: "Register",
        });

        fireEvent.change(usernameInput, {
            target: {
                value: "testUser",
            },
        });
        fireEvent.change(passwordInput, {
            target: {
                value: "testPassword",
            },
        });
        fireEvent.click(registerButton);

        expect(mockMutate).toHaveBeenCalledWith({
            userName: "testUser",
            password: "testPassword",
        });
    });

    // Test Case 7: Handles successful registration
    test("handles successful registration", async () => {
        (useMutation as jest.Mock).mockImplementation((options) => ({
            mutate: (data: any) => {
                options.onSuccess(
                    {
                        ok: true,
                    },
                    data,
                    undefined
                );
            },
            isLoading: false,
            isError: false,
            isSuccess: false,
        }));

        render(<CreateUser />);

        const usernameInput = screen.getByLabelText("User Name");
        const passwordInput = screen.getByLabelText("Password");
        const registerButton = screen.getByRole("button", {
            name: "Register",
        });

        fireEvent.change(usernameInput, {
            target: {
                value: "testUser",
            },
        });
        fireEvent.change(passwordInput, {
            target: {
                value: "testPassword",
            },
        });
        fireEvent.click(registerButton);

        await waitFor(() => {
            expect(mockAddToast).toHaveBeenCalledWith({
                type: NotificationTypeEnum.SUCCESS,
                message: "Successfully registered new user: testUser",
            });
            expect(mockGetByUserId).toHaveBeenCalledWith("testUser");
            expect(reduxStore.dispatch).toHaveBeenCalledWith(
                setSelectedUser({
                    userId: "testUser",
                    roles: [UserRoles.ADMIN],
                    name: "testUser",
                    userName: "testUser",
                })
            );
            expect(reduxStore.dispatch).toHaveBeenCalledWith(
                setUserRoles([UserRoles.ADMIN])
            );
            expect(reduxStore.dispatch).toHaveBeenCalledWith(
                setActiveMode(VisualStateModes.DETAIL)
            );
        });
    });

    // Test Case 8: Handles failed registration
    test("handles failed registration", async () => {
        (useMutation as jest.Mock).mockImplementation((options) => ({
            mutate: (data: any) => {
                options.onError(
                    {
                        data: {
                            message: "User already exists",
                        },
                    },
                    data,
                    undefined
                );
            },
            isLoading: false,
            isError: false,
            isSuccess: false,
        }));

        render(<CreateUser />);

        const usernameInput = screen.getByLabelText("User Name");
        const passwordInput = screen.getByLabelText("Password");
        const registerButton = screen.getByRole("button", {
            name: "Register",
        });

        fireEvent.change(usernameInput, {
            target: {
                value: "testUser",
            },
        });
        fireEvent.change(passwordInput, {
            target: {
                value: "testPassword",
            },
        });
        fireEvent.click(registerButton);

        await waitFor(() => {
            expect(mockAddToast).toHaveBeenCalledWith({
                type: NotificationTypeEnum.ERROR,
                message: "Could not create new user: User already exists",
            });
        });
    });

    // Test Case 9: Handles cancel button click
    test("handles cancel button click", () => {
        render(<CreateUser />);

        const cancelButton = screen.getByRole("button", {
            name: "Cancel",
        });
        fireEvent.click(cancelButton);

        expect(reduxStore.dispatch).toHaveBeenCalledWith(
            setActiveMode(VisualStateModes.LIST)
        );
    });
});
