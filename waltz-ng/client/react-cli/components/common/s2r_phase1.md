## Conversion Context For Common Svelte Components without API calls

### Context
1. Most common svelte components are kept in waltz-ng/client/common/svelte, there might be a few of them that are not a part of 
that folder.
2. Converted components should be purely written in react but may use previously used css classes. The new components should be placed 
in the waltz-ng/client/react-cli/components/common folder. All converted components should be strictly tsx components (typescript).

### For Each Conversion
1. If a component is dependent on any other svelte component we need to convert the dependency as well, but only if the React 
counterpart is not created yet. We need to do this for all nested dependencies.
2. We need to maintain a list in waltz-ng/client/react-cli/components folder called 'Svelte2ReactList' as a markdown file where we store 
the names and relative paths of components that have been converted with a label that they have been converted, we do this for each nested 
component as well (e.g. `CONVERTED {{oldfileName}} {{oldfilePath}} {{newfilePath}} {{comments}}`).
3. If a svelte component is structured as a group eg: waltz-ng/client/svelte/calendar-heatmap then the corresponding React path
should follow the same pattern, and the component must be treated as a group.
4. If a svelte component uses a svelte-store (writable etc.) you can use the redux store available at waltz-ng/client/redux-store.js
and create your slices in waltz-ng/client/redux-slices and register the reducers into the store following the architecture already set up.
5. Any css classes that are referred should be copied and moved into a sibling component.module.css file sibling to the component. This file
will be used to apply css to that component or component group.
6. Both, the component & it's css file should be placed in a folder named after that component in small cased letters e.g.
client/react-cli/components/common/example-component/ExampleComponent.tsx + client/react-cli/components/common/example-component/ExampleComponent.module.css

### Important Context
1. Never try to create a React component that has already been migrated. ALWAYS check the list before creating any components.
2. If a component or any component that it is dependent upon creates an API call, we will not convert that component, we will 
instead add it to the 'Svelte2ReactList' as `SKIPPED {{oldfileName}} {{oldfilePath}} {{newfilePath}} {{comments}}`
3. Any css classes that are referred should be copied and moved into a sibling component.module.css file sibling to the component. This file
will be used to apply css to that component or component group.