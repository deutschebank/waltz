import React, { Component, ErrorInfo, ReactNode } from "react";

interface ErrorBoundaryProps {
    children: ReactNode;
}

interface ErrorBoundaryState {
    hasError: boolean;
    error: Error | null;
    errorInfo: ErrorInfo | null;
}

class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {
    constructor(props: ErrorBoundaryProps) {
        super(props);
        this.state = {
            hasError: false,
            error: null,
            errorInfo: null,
        };
    }

    static getDerivedStateFromError(error: Error): ErrorBoundaryState {
        // Update state to indicate an error has occurred
        return {
            hasError: true,
            error: error,
            errorInfo: null,
        };
    }

    componentDidCatch(error: Error, errorInfo: ErrorInfo): void {
        // Save error details to state
        this.setState({ error, errorInfo });

        // Log error to an external service or console (optional)
        console.error("ErrorBoundary caught an error:", error, errorInfo);
    }

    render(): ReactNode {
        if (this.state.hasError) {
            // Render fallback UI when an error is encountered
            return (
                <div style={{ padding: "20px", border: "1px solid red" }}>
                    <h1>Something went wrong!</h1>
                    <p>
                        We encountered an unexpected error. Please try again
                        later.
                    </p>

                    {/* Optionally display error information in development */}
                    {process.env.NODE_ENV === "development" &&
                        this.state.error && (
                            <details style={{ whiteSpace: "pre-wrap" }}>
                                <strong>{this.state.error.toString()}</strong>
                                <br />
                                {this.state.errorInfo?.componentStack}
                            </details>
                        )}
                </div>
            );
        }

        // Render children when no error is encountered
        return this.props.children;
    }
}

export default ErrorBoundary;
