
import "@testing-library/jest-dom";
import { render, screen, fireEvent } from "@testing-library/react";
import React from "react";
import UserSelectList from "./UserSelectList";
import { userManagementApi } from "../../api/user-management";
import reduxStore from "../../../redux-store";
import { Modes } from "../../enums/User";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

// Mock the API module
jest.mock("../../api/user-management", () => ({
    userManagementApi: {
       findAll: jest.fn()
    },
}));

// Mock the Redux store
jest.mock("../../../redux-store", () => ({
    dispatch: jest.fn(),
}));

const queryClient = new QueryClient();

const renderComponent = () =>
    render(
        <QueryClientProvider client={queryClient}>
            <UserSelectList />
        </QueryClientProvider>
    );

describe("UserSelectList", () => {
    beforeEach(() => {
        // Clear mock history before each test
        jest.clearAllMocks();
        queryClient.clear();
    });

    it("should display a loader while fetching users", () => {
        (userManagementApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["users"],
            queryFn: () => new Promise(() => {}), // Never resolves
        });
        renderComponent();
        expect(screen.getByTestId("loader")).toBeInTheDocument();
    });

    it("should display a list of users", async () => {
        const mockUsers = [
            { userName: "user1", roles: ["roleA"] },
            { userName: "user2", roles: ["roleB"] },
        ];
        (userManagementApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["users"],
            queryFn: () => Promise.resolve(mockUsers),
        });

        renderComponent();

        expect(await screen.findByText("user1")).toBeInTheDocument();
        expect(screen.getByText("user2")).toBeInTheDocument();
    });

    it('should display "no users" message when no users are found', async () => {
        (userManagementApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["users"],
            queryFn: () => Promise.resolve([]),
        });

        renderComponent();

        expect(
            await screen.findByText("There are no users to display")
        ).toBeInTheDocument();
    });

    it("should filter users based on search query", async () => {
        const mockUsers = [
            { userName: "user-alpha", roles: [] },
            { userName: "user-beta", roles: [] },
        ];
        (userManagementApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["users"],
            queryFn: () => Promise.resolve(mockUsers),
        });

        renderComponent();

        await screen.findByText("user-alpha");

        fireEvent.change(screen.getByPlaceholderText("Search for a user..."), {
            target: { value: "beta" },
        });

        expect(screen.queryByText("user-alpha")).not.toBeInTheDocument();
        expect(screen.getByText("user-beta")).toBeInTheDocument();
    });

    it("should show 'too many results' message if more than 100 users are displayed", async () => {
        const mockUsers = Array.from({ length: 101 }, (_, i) => ({
            userName: `user${i}`,
            roles: [],
        }));
        (userManagementApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["users"],
            queryFn: () => Promise.resolve(mockUsers),
        });

        renderComponent();

        expect(
            await screen.findByText(
                "There are too many results to show, please use the search to filter the list"
            )
        ).toBeInTheDocument();
    });

    it("should dispatch actions when a user is selected", async () => {
        const mockUser = { userName: "user-select", roles: ["roleX"] };
        (userManagementApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["users"],
            queryFn: () => Promise.resolve([mockUser]),
        });

        renderComponent();

        const userButton = await screen.findByText("user-select");
        fireEvent.click(userButton);

        expect(reduxStore.dispatch).toHaveBeenCalledWith({
            type: "userManagement/setSelectedUser",
            payload: mockUser,
        });
        expect(reduxStore.dispatch).toHaveBeenCalledWith({
            type: "userManagement/setUserRoles",
            payload: mockUser.roles,
        });
        expect(reduxStore.dispatch).toHaveBeenCalledWith({
            type: "userManagement/setActiveMode",
            payload: Modes.DETAIL,
        });
    });

    it('should dispatch action to set ADD mode when "add a new user" is clicked', async () => {
        (userManagementApi.findAll as jest.Mock).mockReturnValue({
            queryKey: ["users"],
            queryFn: () => Promise.resolve([]),
        });

        renderComponent();

        const addUserButton = await screen.findByTestId("add-user-btn");
        fireEvent.click(addUserButton);

        expect(reduxStore.dispatch).toHaveBeenCalledWith({
            type: "userManagement/setActiveMode",
            payload: Modes.ADD,
        });
    });
});
