## AI-Assisted Svelte to React Conversion Guide (Revision 1)

This guide provides a step-by-step process for converting a Svelte component that makes API calls into a React component, based on the experience of converting the `PersonList` component.

### 1. Initial Analysis

Before you start writing any code, it's crucial to understand the Svelte component you're converting.

*   **Identify the component's props**: Look at the `export let` statements to understand what data the component receives from its parent.
*   **Understand the component's state**: Identify the component's internal state variables (i.e., any non-prop variables that are reassigned).
*   **Locate the API calls**: Find where the component interacts with Svelte stores or makes direct API calls (e.g., using `fetch`).
*   **Analyze the dependencies**: Note any other Svelte components that are imported and used. These will also need to be converted if they haven't been already.

### 2. Scaffolding the React Component

Once you have a clear understanding of the Svelte component, you can start scaffolding the React version.

*   **Create a new directory**: Create a new directory for the component in `/client/react-cli/components/common/` (e.g., `my-component/`).
*   **Create the component file**: Create a new `.tsx` file for your component (e.g., `MyComponent.tsx`).
*   **Create the CSS module**: Create a corresponding `.module.css` file for the component's styles (e.g., `MyComponent.module.css`).
*   **Write the basic component structure**: Create a basic functional component in React, defining the props interface based on your analysis in step 1.
*   **Write the types**: Props defined as interfaces on the component. Types defined in `client/react-cli/types`.

### 3. State Management

Translate the Svelte component's state management to React.

*   **Use the `useState` hook**: For each state variable in the Svelte component, create a corresponding state variable in the React component using the `useState` hook.

### 4. API Calls and Data Fetching

This is the most critical part of the conversion process.

*   **Adapt Svelte stores**: If the Svelte component uses a Svelte store, create a custom React hook to wrap the store. This allows you to use the existing data logic without rewriting it. For example, you can create a `useMyStore.js` that simply returns the Svelte store instance.
*   **Use the `useEffect` hook for data fetching**: If the component fetches data directly, use the `useEffect` hook to perform the data fetching when the component mounts or when its dependencies change.
*   **Handle asynchronous operations**: Use `async/await` or promises to handle asynchronous API calls, and use a `loading` state variable to provide feedback to the user.

### 5. Event Handling

Translate the Svelte component's event handling to React.

*   **Use `onClick` and other event handlers**: Replace Svelte's `on:click` directive with React's `onClick` prop.
*   **Stop event propagation**: If you encounter issues with event bubbling (e.g., a click in your component triggers an action in a parent component), use `event.stopPropagation()` in your event handlers to prevent the event from propagating up the DOM tree.

### 6. Styling

Migrate the component's styles to the new CSS module.

*   **Copy the styles**: Copy the CSS rules from the Svelte component's `<style>` block to the new `.module.css` file.
*   **Update the class names**: Update the class names in your React component to use the imported `styles` object (e.g., `className={styles.myClass}`).

### 7. Type Safety

Ensure that your new component is type-safe.

*   **Define interfaces for props and data models**: Create TypeScript interfaces for the component's props and any data models that are used (e.g., the `Person` interface).
*   **Use the interfaces in your component**: Use the interfaces to type the component's props, state variables, and function parameters.

### 8. Handling Dependencies

Be mindful of the dependencies you introduce.

*   **Avoid new dependencies**: If possible, avoid adding new third-party dependencies to the project. If the Svelte component used a library, try to find a way to achieve the same functionality using existing libraries or by building a custom solution.
*   **Update import paths**: Update the import paths in any files that use the new component to reflect its new location.

By following these steps, you can convert a Svelte component that makes API calls into a robust, type-safe, and maintainable React component.
