import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import Button from "./Button";
import "@testing-library/jest-dom";

describe("Button Component", () => {
    test("renders the button with children", () => {
        // Arrange
        render(<Button>Click me</Button>);

        // Assert
        const buttonElement = screen.getByTestId("custom-button");
        expect(buttonElement).toBeInTheDocument();
        expect(buttonElement).toHaveTextContent("Click me");
    });

    test("calls onClick when clicked", () => {
        const mockOnClick = jest.fn();
        render(<Button onClick={mockOnClick}>Click me</Button>);

        const buttonElement = screen.getByTestId("custom-button");
        fireEvent.click(buttonElement); // Simulate a click
        expect(mockOnClick).toHaveBeenCalled(); // Check if onClick was called
    });

    test("disables the button when disabled prop is true", () => {
        render(<Button disabled>Click me</Button>);

        const buttonElement = screen.getByTestId("custom-button");
        expect(buttonElement).toBeDisabled(); // Check if button is disabled
    });

    // test("applies custom className", () => {
    //     render(<Button className="my-custom-class">Click me</Button>);

    //     const buttonElement = screen.getByTestId("custom-button");
    //     expect(buttonElement).toHaveClass("my-custom-class");
    // });

    // test("has default type of 'button'", () => {
    //     render(<Button>Click me</Button>);

    //     const buttonElement = screen.getByTestId("custom-button");
    //     expect(buttonElement).toHaveAttribute("type", "button");
    // });
});