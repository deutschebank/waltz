import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import React from "react";
import { configureStore, Store } from "@reduxjs/toolkit";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import UserRolesList from "../UserRolesList";
import { useToasts } from "../../../context/toast/ToastContext";
import { roleApi } from "../../../api/roles";
import { userManagementApi } from "../../../api/user-management";
import userManagementSlice from "../../../../redux-slices/user-management-slice";
import { VisualStateModes } from "../../../enums/VisualState";
import reduxStore from "../../../../redux-store";
import { UserManagementState } from "../../../types/User";

// Mock dependencies
jest.mock("../../../context/toast/ToastContext", () => ({
    useToasts: jest.fn(),
}));

jest.mock("../../../api/roles", () => ({
    findAll: jest.fn(),
}));

jest.mock("../../../api/user-management", () => ({
    userManagementApi: {
       updateRoles: jest.fn()
    },
}));

jest.mock("../../../api/roles", () => ({
    roleApi: {
       findAll: jest.fn()
    },
}));

jest.mock("../../../../redux-store", () => ({
    __esModule: true,
    default: {
        getState: jest.fn(),
        dispatch: jest.fn(),
        subscribe: jest.fn(),
    },
}));

const mockAddToast = jest.fn();

const allRoles = [
    {
        id: 1,
        key: "ADMIN",
        name: "Admin",
        userSelectable: true,
        isCustom: false,
        description: "Admin role",
    },
    {
        id: 2,
        key: "USER",
        name: "User",
        userSelectable: true,
        isCustom: false,
        description: "User role",
    },
    {
        id: 3,
        key: "READ_ONLY",
        name: "Read Only",
        userSelectable: false,
        isCustom: false,
        description: "Read only role",
    },
];

const mockSelectedUser = {
    userName: "testuser",
    roles: ["USER"],
};

const queryClient = new QueryClient({
    defaultOptions: {
        queries: {
            retry: false,
        },
    },
});

let store: Store<{ userManagement: UserManagementState }>;

const renderComponent = (selectedUser: any = mockSelectedUser) => {
    store = configureStore({
        reducer: {
            // @ts-ignore
            userManagement: userManagementSlice,
        },
        preloadedState: {
            userManagement: {
                selectedUser,
                userRoles: selectedUser?.roles || [],
                activeMode: VisualStateModes.LIST,
            },
        },
    });

    (reduxStore.getState as jest.Mock).mockImplementation(store.getState);
    (reduxStore.dispatch as jest.Mock).mockImplementation(store.dispatch);
    (reduxStore.subscribe as jest.Mock).mockImplementation(store.subscribe);

    return render(
        <QueryClientProvider client={queryClient}>
            <UserRolesList />
        </QueryClientProvider>
    );
};

describe("UserRolesList", () => {
    beforeEach(() => {
        jest.clearAllMocks();
        (roleApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["roles"],
            queryFn: () => Promise.resolve(allRoles),
        });
        (useToasts as jest.Mock).mockReturnValue({
            addToast: mockAddToast,
        });
    });

    it("should render no data when no user is selected", () => {
        renderComponent(null);
        expect(
            screen.getByText("There is no selected user")
        ).toBeInTheDocument();
    });

    it("should render user roles and user information", async () => {
        renderComponent();
        await waitFor(() => {
            expect(screen.getByText("testuser")).toBeInTheDocument();
            expect(
                screen.getByPlaceholderText("Search for a role...")
            ).toBeInTheDocument();
            expect(screen.getByText("Admin")).toBeInTheDocument();
            expect(screen.getByText("User")).toBeInTheDocument();
        });
    });

    it("should allow searching for roles", async () => {
        renderComponent();
        await waitFor(() => {
            const searchInput = screen.getByPlaceholderText(
                "Search for a role..."
            );
            fireEvent.change(searchInput, { target: { value: "Admin" } });
            expect(screen.getByText("Admin")).toBeInTheDocument();
            expect(screen.queryByText("User")).not.toBeInTheDocument();
        });
    });

    it("should handle role selection", async () => {
        renderComponent();

        let adminCheckbox: HTMLElement;
        await waitFor(() => {
            adminCheckbox = screen.getAllByRole("checkbox")[0];
            expect(adminCheckbox).not.toBeChecked();
        });

        fireEvent.click(adminCheckbox!);
        await waitFor(() => {
            expect(adminCheckbox).toBeChecked();

            const state = store.getState().userManagement;
            expect(state.userRoles).toContain("ADMIN");
        });
    });

    it("should handle add all and remove all", async () => {
        renderComponent();

        await waitFor(() => {
            expect(screen.getByText("Admin")).toBeInTheDocument();
        });

        const addButton = screen.getByTestId("add-all");
        fireEvent.click(addButton);

        await waitFor(() => {
            let state = store.getState().userManagement;
            expect(state.userRoles).toEqual(
                expect.arrayContaining(["USER", "ADMIN"])
            );
        });

        const removeButton = screen.getByTestId("remove-all");
        fireEvent.click(removeButton);

        await waitFor(() => {
            const state = store.getState().userManagement;
            expect(state.userRoles).not.toContain("ADMIN");
            expect(state.userRoles).not.toContain("USER");
        });
    });

    it("should enable save button when roles are changed", async () => {
        renderComponent();
        let saveButton: HTMLElement;
        let adminCheckbox: HTMLElement;

        await waitFor(() => {
            saveButton = screen.getByTestId("save-updates");
            adminCheckbox = screen.getAllByRole("checkbox")[0];
            expect(saveButton).toBeDisabled();
        });

        fireEvent.click(adminCheckbox!);

        await waitFor(() => {
            expect(saveButton).not.toBeDisabled();
        });
    });

    it("should handle saving updated roles successfully", async () => {
        const mockUpdateFn = jest.fn().mockResolvedValue(1);
        (userManagementApi.updateRoles as jest.Mock).mockReturnValue({
            mutationFn: mockUpdateFn,
        });

        renderComponent();
        let adminCheckbox: HTMLElement;
        await waitFor(() => {
            adminCheckbox = screen.getAllByRole("checkbox")[0];
        });

        fireEvent.click(adminCheckbox!);

        let saveButton: HTMLElement;
        await waitFor(() => {
            saveButton = screen.getByTestId("save-updates");
            expect(saveButton).not.toBeDisabled();
        });

        fireEvent.click(saveButton!);

        await waitFor(() => {
            expect(mockUpdateFn).toHaveBeenCalled();
        });

        expect(mockAddToast).toHaveBeenCalledWith({
            type: "SUCCESS",
            message: "Successfully updated roles for testuser",
        });

        await waitFor(() => {
            expect(store.getState().userManagement.activeMode).toBe(VisualStateModes.LIST);
        });
    });

    it("should handle saving updated roles with an error", async () => {
        const error = { data: { message: "Failed to update" } };
        const mockUpdateFn = jest.fn().mockRejectedValue(error);
        (userManagementApi.updateRoles as jest.Mock).mockReturnValue({
            mutationFn: mockUpdateFn,
        });

        renderComponent();
        let adminCheckbox: HTMLElement;
        await waitFor(() => {
            adminCheckbox = screen.getAllByRole("checkbox")[0];
        });
        fireEvent.click(adminCheckbox!);

        let saveButton: HTMLElement;
        await waitFor(() => {
            saveButton = screen.getByTestId("save-updates");
        });
        fireEvent.click(saveButton!);

        await waitFor(() => {
            expect(mockUpdateFn).toHaveBeenCalled();
        });

        expect(mockAddToast).toHaveBeenCalledWith({
            type: "ERROR",
            message: "Unable to update roles: Failed to update",
        });
    });

    it("should handle cancel", async () => {
        renderComponent();
        let adminCheckbox: HTMLElement;
        await waitFor(() => {
            adminCheckbox = screen.getAllByRole("checkbox")[0];
        });
        fireEvent.click(adminCheckbox!);

        const cancelButton = screen.getByTestId("cancel");
        fireEvent.click(cancelButton);

        expect(mockAddToast).toHaveBeenCalledWith({
            type: "WARNING",
            message: "Changes to roles for testuser discarded",
        });
        expect(store.getState().userManagement.activeMode).toBe(VisualStateModes.LIST);
    });

    it("should display and toggle read-only roles", async () => {
        const userWithReadOnly = {
            ...mockSelectedUser,
            roles: ["USER", "READ_ONLY"],
        };
        renderComponent(userWithReadOnly);

        await waitFor(() => {
            expect(
                screen.getByText(/This user has 1 roles which are read only/)
            ).toBeInTheDocument();
        });

        const showButton = screen.getByRole("button", {
            name: /Show additional roles/,
        });
        fireEvent.click(showButton);

        await waitFor(() => {
            expect(screen.getByText("Read Only")).toBeInTheDocument();
        });

        const hideButton = screen.getByRole("button", {
            name: /Hide additional roles/,
        });
        fireEvent.click(hideButton);
        await waitFor(() => {
            expect(screen.queryByText("Read Only")).not.toBeInTheDocument();
        });
    });
});
