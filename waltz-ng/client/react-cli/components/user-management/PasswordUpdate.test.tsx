import React from "react";
import { render, fireEvent, screen, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import PasswordUpdatePanel from "./PasswordUpdate";
import { useSliceSelector } from "../../hooks/useSliceSelector";
import reduxStore from "../../../redux-store";
import { useToasts } from "../../context/ToastContext";
import { useMutation } from "@tanstack/react-query";
import { setActiveMode } from "../../../redux-slices/user-management-slice";
import { Modes } from "../../enums/User";
import { NotificationTypeEnum } from "../../enums/Notification";
import { userManagementApi } from "../../api/user-management";

// Mock dependencies
jest.mock("../../hooks/useSliceSelector");
jest.mock("../../../redux-store", () => ({
    dispatch: jest.fn(),
}));
jest.mock("../../context/ToastContext");
jest.mock("../../api/user-management", () => ({
    userManagementApi: {
        resetPassword: jest.fn()
    },
}));
jest.mock("@tanstack/react-query");
jest.mock("../../../redux-slices/user-management-slice", () => ({
    setActiveMode: jest.fn(),
}));

const mockUseSliceSelector = useSliceSelector as jest.Mock;
const mockAddToast = jest.fn();
const mockUseToasts = useToasts as jest.Mock;
const mockResetPasswordApi = userManagementApi.resetPassword as jest.Mock; // This line is not used in the test, can be removed or kept as is.
const mockUseMutation = useMutation as jest.Mock;
const mockSetActiceMode = setActiveMode as unknown as jest.Mock;
const mockDispatch = reduxStore.dispatch as jest.Mock;

describe("PasswordUpdatePanel", () => {
    beforeEach(() => {
        jest.clearAllMocks();
        mockUseToasts.mockReturnValue({ addToast: mockAddToast });
    });

    // it("should render no user selected message if no user is selected", () => {
    //     mockUseSliceSelector.mockReturnValue(null);
    //     render(<PasswordUpdatePanel />);
    //     expect(
    //         screen.getByText("No user selected to update password for.")
    //     ).toBeInTheDocument();
    // });

    it("should render the password update form for a selected user", () => {
        mockUseSliceSelector.mockReturnValue({ userName: "testuser" });
        mockUseMutation.mockReturnValue({ mutate: jest.fn() });
        render(<PasswordUpdatePanel />);
        expect(
            screen.getByText("Password Update for testuser")
        ).toBeInTheDocument();
        expect(screen.getByLabelText("New Password")).toBeInTheDocument();
        expect(screen.getByLabelText("Re-type Password")).toBeInTheDocument();
        expect(
            screen.getByRole("button", { name: "Reset Password" })
        ).toBeInTheDocument();
        expect(
            screen.getByRole("button", { name: "Cancel" })
        ).toBeInTheDocument();
    });

    it("should have disabled reset button initially and when passwords do not match", () => {
        mockUseSliceSelector.mockReturnValue({ userName: "testuser" });
        mockUseMutation.mockReturnValue({ mutate: jest.fn() });
        render(<PasswordUpdatePanel />);

        const newPasswordInput = screen.getByLabelText("New Password");
        const retypePasswordInput = screen.getByLabelText("Re-type Password");
        const resetButton = screen.getByRole("button", {
            name: "Reset Password",
        });

        expect(resetButton).toBeDisabled();

        fireEvent.change(newPasswordInput, {
            target: { value: "password123" },
        });
        expect(resetButton).toBeDisabled();

        fireEvent.change(retypePasswordInput, {
            target: { value: "password456" },
        });
        expect(resetButton).toBeDisabled();
    });

    it("should enable reset button when passwords match and are not empty", () => {
        mockUseSliceSelector.mockReturnValue({ userName: "testuser" });
        mockUseMutation.mockReturnValue({ mutate: jest.fn() });
        render(<PasswordUpdatePanel />);

        const newPasswordInput = screen.getByLabelText("New Password");
        const retypePasswordInput = screen.getByLabelText("Re-type Password");
        const resetButton = screen.getByRole("button", {
            name: "Reset Password",
        });

        fireEvent.change(newPasswordInput, {
            target: { value: "password123" },
        });
        fireEvent.change(retypePasswordInput, {
            target: { value: "password123" },
        });

        expect(resetButton).toBeEnabled();
    });

    it("should call reset password mutation with the new password", () => {
        const mutate = jest.fn();
        mockUseSliceSelector.mockReturnValue({ userName: "testuser" });
        mockUseMutation.mockReturnValue({ mutate });
        render(<PasswordUpdatePanel />);

        const newPasswordInput = screen.getByLabelText("New Password");
        const retypePasswordInput = screen.getByLabelText("Re-type Password");
        const resetButton = screen.getByRole("button", {
            name: "Reset Password",
        });

        fireEvent.change(newPasswordInput, {
            target: { value: "password123" },
        });
        fireEvent.change(retypePasswordInput, {
            target: { value: "password123" },
        });
        fireEvent.click(resetButton);

        expect(mutate).toHaveBeenCalledWith("password123");
    });

    it("should dispatch setActiveMode to DETAIL on cancel", () => {
        mockUseSliceSelector.mockReturnValue({ userName: "testuser" });
        mockUseMutation.mockReturnValue({ mutate: jest.fn() });
        render(<PasswordUpdatePanel />);

        const cancelButton = screen.getByRole("button", { name: "Cancel" });
        fireEvent.click(cancelButton);

        expect(mockSetActiceMode).toHaveBeenCalledWith(Modes.DETAIL);
        expect(mockDispatch).toHaveBeenCalledWith(
            mockSetActiceMode(Modes.DETAIL)
        );
    });

    describe("API interactions", () => {
        const selectedUser = { userName: "testuser" };

        it("should show success toast and navigate to detail view on successful password reset", async () => {
            const mutationFn = jest.fn().mockResolvedValue(true);
            mockResetPasswordApi.mockReturnValue({ mutationFn });

            let onSuccessCallback: (res: boolean) => void = () => {};
            mockUseMutation.mockImplementation(({ onSuccess }) => {
                onSuccessCallback = onSuccess;
                return { mutate: jest.fn() };
            });

            mockUseSliceSelector.mockReturnValue(selectedUser);
            render(<PasswordUpdatePanel />);

            onSuccessCallback(true);

            await waitFor(() => {
                expect(mockAddToast).toHaveBeenCalledWith({
                    type: NotificationTypeEnum.SUCCESS,
                    message: `Successfully updated password for user: ${selectedUser.userName}`,
                });
            });

            await waitFor(() => {
                expect(mockDispatch).toHaveBeenCalledWith(
                    mockSetActiceMode(Modes.DETAIL)
                );
            });
        });

        it("should show error toast on failed password reset", async () => {
            const error = { data: { message: "Failed to update" } };
            const mutationFn = jest.fn().mockRejectedValue(error);
            mockResetPasswordApi.mockReturnValue({ mutationFn });

            let onErrorCallback: (error: any) => void = () => {};
            mockUseMutation.mockImplementation(({ onError }) => {
                onErrorCallback = onError;
                return { mutate: jest.fn() };
            });

            mockUseSliceSelector.mockReturnValue(selectedUser);
            render(<PasswordUpdatePanel />);

            onErrorCallback(error);

            await waitFor(() => {
                expect(mockAddToast).toHaveBeenCalledWith({
                    type: NotificationTypeEnum.ERROR,
                    message: `Could not update password: ${error.data.message}`,
                });
            });
        });
    });
});
