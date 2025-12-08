import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import Loader from "./Loader";

describe("Loader", () => {
    it("should render the loader with the correct text", () => {
        render(<Loader />);
        expect(screen.getByText("Loading, please wait...")).toBeInTheDocument();
    });

    it("should have the spinner element", () => {
        const { getByTestId } = render(<Loader />);
        const loaderElement = getByTestId("loader");
        const spinnerElement = loaderElement.querySelector(".spinner");
        expect(spinnerElement).toBeInTheDocument();
    });
});