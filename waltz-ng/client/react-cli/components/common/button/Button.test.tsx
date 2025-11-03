import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import Button from "./Button";
import "@testing-library/jest-dom";

describe("Button Component", () => {
    test("renders the button with given label", () => {
        // Arrange
        render(<Button label="Click me" onClick={() => {}} />);

        // Assert
        const buttonElement = screen.getByTestId("custom-button");
        expect(buttonElement).toBeInTheDocument();
        expect(buttonElement).toHaveTextContent("Click me");
    });

    test("calls onClick when clicked", () => {
        const mockOnClick = jest.fn();
        render(<Button label="Click me" onClick={mockOnClick} />);

        const buttonElement = screen.getByTestId("custom-button");
        fireEvent.click(buttonElement); // Simulate a click
        expect(mockOnClick).toHaveBeenCalled(); // Check if onClick was called
    });

    test("disables the button when `disabled` prop is true", () => {
        render(<Button label="Click me" onClick={() => {}} disabled />);

        const buttonElement = screen.getByTestId("custom-button");
        expect(buttonElement).toBeDisabled(); // Check if button is disabled
    });
});
