import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import UserManagementPanel from "./UserManagementPanel";
import { useSliceSelector } from "../../hooks/useSliceSelector";
import { Modes } from "../../enums/User";

// Mock child components to isolate the UserManagementPanel
jest.mock(
    "../../components/common/Icon",
    () =>
        ({ name }: { name: string }) =>
            <span>{name}</span>
);
jest.mock("../../components/user-management/UserSelectList", () => () => (
    <div>UserSelectList</div>
));
jest.mock("../../components/user-management/CreateUser", () => () => (
    <div>CreateUser</div>
));
jest.mock("../../components/user-management/DeleteUser", () => () => (
    <div>DeleteUser</div>
));
jest.mock("../../components/user-management/PasswordUpdate", () => () => (
    <div>PasswordUpdate</div>
));
jest.mock("../../components/user-management/UserRolesList", () => () => (
    <div>UserRolesList</div>
));
jest.mock("../../components/user-management/UserBulkEditor", () => () => (
    <div>UserBulkEditor</div>
));

// Mock the custom Redux selector hook
jest.mock("../../hooks/useSliceSelector");
const mockUseSliceSelector = useSliceSelector as jest.Mock;

// Mock the SCSS module to prevent style-related errors
jest.mock("./UserManagementPanel.module.scss", () => ({
    waltzTabs: "waltzTabs",
    wtlabel: "wtlabel",
    wtTab: "wtTab",
    wtActive: "wtActive",
}));

describe("UserManagementPanel", () => {
    beforeEach(() => {
        // Reset mocks before each test
        mockUseSliceSelector.mockClear();
    });

    it("should render correctly with the 'Individual User Admin' tab selected by default", () => {
        mockUseSliceSelector.mockReturnValue(Modes.LIST);
        render(<UserManagementPanel />);

        // Check if tabs are rendered
        expect(
            screen.getByText(/Individual User Admin/i).closest("label")
        ).toBeInTheDocument();
        expect(
            screen.getByText(/Bulk User Admin/i).closest("label")
        ).toBeInTheDocument();

        // Check if the 'Individual' tab is selected by default

        expect(screen.getByTestId("single")).toBeChecked();
        expect(screen.getByTestId("bulk")).not.toBeChecked();

        // Check if the correct content for the default mode is rendered
        expect(screen.getByText("UserSelectList")).toBeInTheDocument();
        expect(screen.queryByText("UserBulkEditor")).not.toBeInTheDocument();
    });

    it("should switch to the 'Bulk User Admin' tab and render UserBulkEditor", () => {
        mockUseSliceSelector.mockReturnValue(Modes.LIST);
        render(<UserManagementPanel />);

        const bulkTab = screen.getByTestId("bulk");
        fireEvent.click(bulkTab);

        expect(bulkTab).toBeChecked();
        expect(screen.getByTestId("single")).not.toBeChecked();

        // Verify that the bulk editor is now visible and single user content is not
        expect(screen.getByText("UserBulkEditor")).toBeInTheDocument();
        expect(screen.queryByText("UserSelectList")).not.toBeInTheDocument();
    });

    // Test cases for different modes in single user tab
    const singleUserModes = [
        { mode: Modes.LIST, component: "UserSelectList" },
        { mode: Modes.DETAIL, component: "UserRolesList" },
        { mode: Modes.ADD, component: "CreateUser" },
        { mode: Modes.PASSWORD, component: "PasswordUpdate" },
        { mode: Modes.DELETE, component: "DeleteUser" },
    ];

    singleUserModes.forEach(({ mode, component }) => {
        it(`should render ${component} when activeMode is ${mode}`, () => {
            mockUseSliceSelector.mockReturnValue(mode);
            render(<UserManagementPanel />);

            // Ensure the 'Individual' tab is active
            fireEvent.click(screen.getByTestId("single"));

            expect(screen.getByText(component)).toBeInTheDocument();
        });
    });

    it("should render UserSelectList by default if activeMode is unknown", () => {
        // Simulate an unknown or undefined mode
        mockUseSliceSelector.mockReturnValue("unknown_mode");
        render(<UserManagementPanel />);

        expect(screen.getByText("UserSelectList")).toBeInTheDocument();
    });

    it("should switch back to 'Individual User Admin' tab from 'Bulk User Admin' tab", () => {
        mockUseSliceSelector.mockReturnValue(Modes.LIST);
        render(<UserManagementPanel />);

        const individualTab = screen.getByTestId("single");
        const bulkTab = screen.getByTestId("bulk");

        // Switch to bulk
        fireEvent.click(bulkTab);
        expect(screen.getByText("UserBulkEditor")).toBeInTheDocument();

        // Switch back to individual
        fireEvent.click(individualTab);
        expect(screen.getByText("UserSelectList")).toBeInTheDocument();
        expect(screen.queryByText("UserBulkEditor")).not.toBeInTheDocument();
    });
});
