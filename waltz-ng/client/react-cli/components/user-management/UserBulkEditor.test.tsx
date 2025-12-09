import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import { useMutation } from "@tanstack/react-query";
import { useToasts } from "../../context/toast/ToastContext";
import { userManagementApi } from "../../api/user-management";
import { NotificationTypeEnum } from "../../enums/Notification";
import UserBulkEditor from "./UserBulkEditor";

// Mock dependencies
jest.mock("@tanstack/react-query", () => ({
    useMutation: jest.fn(),
}));

jest.mock("../../context/toast/ToastContext", () => ({
    useToasts: jest.fn(),
}));

jest.mock("../../api/user-management", () => ({
    userManagementApi: {
        bulkUploadPreview: jest.fn(),
        bulkUpload: jest.fn(),
    },
}));

const mockUseMutation = useMutation as jest.Mock;
const mockUseToasts = useToasts as jest.Mock;
const mockBulkUploadPreview = userManagementApi.bulkUploadPreview as jest.Mock;
const mockBulkUpload = userManagementApi.bulkUpload as jest.Mock;

describe("UserBulkEditor", () => {
    const mockAddToast = jest.fn();
    let mutate: jest.Mock;

    beforeEach(() => {
        jest.clearAllMocks();
        mutate = jest.fn();
        mockUseToasts.mockReturnValue({ addToast: mockAddToast });
        mockUseMutation.mockReturnValue({
            mutate,
            isPending: false,
        });
        mockBulkUploadPreview.mockReturnValue({ mutationFn: jest.fn() });
        mockBulkUpload.mockReturnValue({ mutationFn: jest.fn() });
    });

    const renderComponent = () => render(<UserBulkEditor />);

    describe("Edit Mode", () => {
        it("should render the initial edit form correctly", () => {
            renderComponent();
            expect(screen.getByText("Upload Mode")).toBeInTheDocument();
            expect(screen.getByLabelText("Add Only")).toBeChecked();
            expect(screen.getByLabelText("Remove Only")).not.toBeChecked();
            expect(screen.getByLabelText("Replace")).not.toBeChecked();
            expect(
                screen.getByPlaceholderText("username, role, comment")
            ).toBeInTheDocument();
            expect(screen.getByTestId("preview-button")).toBeDisabled();
        });

        it("should enable preview button when text is entered", () => {
            renderComponent();
            const textArea = screen.getByPlaceholderText(
                "username, role, comment"
            );
            fireEvent.change(textArea, { target: { value: "test,ADMIN," } });
            expect(screen.getByTestId("preview-button")).toBeEnabled();
        });

        it("should change upload mode on radio button click", () => {
            renderComponent();
            const removeRadio = screen.getByLabelText("Remove Only");
            fireEvent.click(removeRadio);
            expect(removeRadio).toBeChecked();
        });

        it("should call preview mutation on form submission", () => {
            renderComponent();
            const textArea = screen.getByPlaceholderText(
                "username, role, comment"
            );
            fireEvent.change(textArea, { target: { value: "test,ADMIN," } });
            const previewButton = screen.getByTestId("preview-button");
            fireEvent.click(previewButton);
            expect(mutate).toHaveBeenCalledTimes(1);
        });

        it("should show previewing state when previewing", () => {
            mockUseMutation.mockReturnValue({ mutate, isPending: true });
            renderComponent();
            const previewButton = screen.getByRole("button", {
                name: "Previewing...",
            });
            expect(previewButton).toBeDisabled();
        });

        it("should handle preview API error", async () => {
            const error = { data: { message: "Preview failed" } };
            mockUseMutation.mockImplementation(({ onError }: any) => ({
                mutate: () => onError(error),
                isPending: false,
            }));
            renderComponent();
            const textArea = screen.getByPlaceholderText(
                "username, role, comment"
            );
            fireEvent.change(textArea, { target: { value: "test,ADMIN," } });
            fireEvent.click(screen.getByTestId("preview-button"));

            await waitFor(() => {
                expect(mockAddToast).toHaveBeenCalledWith({
                    type: NotificationTypeEnum.ERROR,
                    message: "Could not preview bulk upload: Preview failed",
                });
            });
        });
    });

    describe("Preview Mode", () => {
        const previewData = [
            {
                givenUser: "test.user",
                givenRole: "ADMIN",
                givenComment: "comment",
                resolvedUser: "test.user",
                resolvedRole: "ADMIN",
                resolvedComment: "comment",
            },
            {
                givenUser: "bad.user",
                givenRole: "BAD_ROLE",
                givenComment: "bad comment",
                resolvedUser: null,
                resolvedRole: null,
                resolvedComment: null,
            },
        ];

        beforeEach(() => {
            mockUseMutation.mockImplementation(({ onSuccess }: any) => ({
                mutate: () => onSuccess({ data: previewData }),
                isPending: false,
            }));
            renderComponent();
            const textArea = screen.getByPlaceholderText(
                "username, role, comment"
            );
            fireEvent.change(textArea, { target: { value: "some data" } });
            fireEvent.click(screen.getByTestId("preview-button"));
        });

        it("should render preview mode correctly after successful preview", async () => {
            await waitFor(() => {
                expect(screen.getByText("Preview")).toBeInTheDocument();
                expect(screen.getByText("ADD_ONLY")).toBeInTheDocument(); // Default upload mode
                expect(screen.getByText("test.user")).toBeInTheDocument();
                expect(screen.getByText("bad.user")).toBeInTheDocument();
                expect(
                    screen.getAllByRole("row")[1].querySelector(".success")
                ).not.toBeNull();
                expect(
                    screen.getAllByRole("row")[2].querySelector(".danger")
                ).not.toBeNull();
                expect(
                    screen.getByRole("button", { name: "Update" })
                ).toBeInTheDocument();
                expect(
                    screen.getByRole("button", { name: "Back" })
                ).toBeInTheDocument();
            });
        });

        // it('should switch back to edit mode when "Back" is clicked', async () => {
        //     await waitFor(() => {
        //         expect(screen.getByText("Preview")).toBeInTheDocument();
        //     });
        //     fireEvent.click(screen.getByTestId("back-preview"));
        //     expect(screen.getByText("Upload Mode")).toBeInTheDocument();
        // });

        // it("should call upload mutation on update", async () => {
        //     const uploadMutate = jest.fn();
        //     mockUseMutation
        //         .mockImplementationOnce(({ onSuccess }: any) => ({
        //             // for preview
        //             mutate: () => onSuccess({ data: previewData }),
        //             isPending: false,
        //         }))
        //         .mockImplementationOnce(() => ({
        //             // for upload
        //             mutate: uploadMutate,
        //             isPending: false,
        //         }));

        //     render(<UserBulkEditor />);
        //     fireEvent.change(
        //         screen.getByPlaceholderText("username, role, comment"),
        //         { target: { value: "test.user, ADMIN, comment" } }
        //     );
        //     fireEvent.click(screen.getByTestId("preview-button"));

        //     await waitFor(() => {
        //         fireEvent.click(
        //             screen.getAllByRole("button", { name: "Update" })[0]
        //         );
        //     });

        //     expect(uploadMutate).toHaveBeenCalledTimes(1);
        // });

        // it("should handle upload API error", async () => {
        //     const error = { data: { message: "Upload failed" } };
        //     mockUseMutation
        //         .mockImplementationOnce(({ onSuccess }: any) => ({
        //             mutate: () => onSuccess({ data: previewData }),
        //             isPending: false,
        //         }))
        //         .mockImplementationOnce(({ onError }: any) => ({
        //             mutate: () => onError(error),
        //             isPending: false,
        //         }));

        //     render(<UserBulkEditor />);
        //     fireEvent.change(
        //         screen.getByPlaceholderText("username, role, comment"),
        //         { target: { value: "some data" } }
        //     );
        //     fireEvent.click(screen.getByTestId("preview-button"));

        //     await waitFor(() => {
        //         fireEvent.click(screen.getByRole("button", { name: "Update" }));
        //     });

        //     await waitFor(() => {
        //         expect(mockAddToast).toHaveBeenCalledWith({
        //             type: NotificationTypeEnum.ERROR,
        //             message: "Could not perform bulk upload: Upload failed",
        //         });
        //     });
        // });
    });

    // describe("Result Mode", () => {
    //     it("should render result mode correctly after successful upload", async () => {
    //         mockUseMutation
    //             .mockImplementationOnce(({ onSuccess }: any) => ({
    //                 // preview
    //                 mutate: () => onSuccess({ data: [] }),
    //                 isPending: false,
    //             }))
    //             .mockImplementationOnce(({ onSuccess }: any) => ({
    //                 // upload
    //                 mutate: () => onSuccess({ data: 5 }),
    //                 isPending: false,
    //             }));

    //         renderComponent();
    //         fireEvent.change(
    //             screen.getByPlaceholderText("username, role, comment"),
    //             { target: { value: "some data" } }
    //         );
    //         fireEvent.click(screen.getByTestId("preview-button"));

    //         await waitFor(() => {
    //             fireEvent.click(screen.getByRole("button", { name: "Update" }));
    //         });

    //         await waitFor(() => {
    //             expect(screen.getByText("Result")).toBeInTheDocument();
    //             expect(
    //                 screen.getByText("Updated: 5 user/role entries.")
    //             ).toBeInTheDocument();
    //             expect(
    //                 screen.getByRole("button", { name: "Back" })
    //             ).toBeInTheDocument();
    //         });

    //         fireEvent.click(screen.getByRole("button", { name: "Back" }));
    //         expect(screen.getByText("Upload Mode")).toBeInTheDocument();
    //     });
    // });
});
