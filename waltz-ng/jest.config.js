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
    collectCoverageFrom: ["client/react-cli/**/*.{js,jsx,ts,tsx}"],
    // Collect coverage only from these specific file patterns, excluding unnecessary files/folders
    coveragePathIgnorePatterns: [
        "client/react-cli/api/", // Exclude api directories
        "client/react-cli/constants/", // Exclude constants directories
        "client/react-cli/utils/", // Exclude utility functions
        "client/react-cli/types/", // Exclude types
        "client/react-cli/enums/", // Exclude enums
        "\\.d\\.ts", // Exclude TypeScript declaration files
        "generate-svelte-tree.js", // Exclude
    ],
    coverageThreshold: {
        global: {
            branches: 90, // At least 90% of branches must be covered
            functions: 90, // At least 90% of functions must be covered
            lines: 90, // At least 90% of lines must be executed
            statements: 90, // At least 90% of statements must be executed
        },
    },
    coverageReporters: [
        "json", // JSON file
        "text", // Text output on the terminal
        "lcov", // LCov format for CI tools or HTML viewing
    ],
};
