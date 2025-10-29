module.exports = {
    preset: "ts-jest",
    testEnvironment: "jsdom", // Ensure a DOM-like environment for React tests
    transform: {
        "^.+\\.tsx?$": "ts-jest",
    },
    moduleNameMapper: {
        "\\.(css|scss)$": "identity-obj-proxy", // Mock styles
    },
    setupFilesAfterEnv: ["@testing-library/jest-dom"], // Include setup for React Testing Library
    collectCoverage: true,
    coverageThreshold: {
        global: {
            branches: 80, // At least 80% of branches must be covered
            functions: 80, // At least 80% of functions must be covered
            lines: 80, // At least 80% of lines must be executed
            statements: 80, // At least 80% of statements must be executed
        },
    },
    coverageReporters: [
        "json", // JSON file
        "text", // Text output on the terminal
        "lcov", // LCov format for CI tools or HTML viewing
    ],
};
