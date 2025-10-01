/**
 * If you want to navigate using redux, import the store to the component,
 * and then use:-
 *
 * ```reduxStore.dispatch(navigate({
 *     state: "main.your.state",
 *     params: {id: 22},
 *     isNotification: false, // (optional -> if true then will not navigate)
 *     options: {} // angularjs options (optional)
 * });
 * ```
 *
 */

import {createSlice} from "@reduxjs/toolkit";

const pageNavSlice = createSlice({
    name: 'pageNav',
    initialState: {
        state: null,
        params: null,
        options: null,
        isNotification: false
    },
    reducers: {
        navigate: (state, action) => {
            state.state = action.payload.state;
            state.params = action.payload.params;
            state.options = action.payload.options;
            state.isNotification = action.payload.isNotification;
        }
    }
});

export const { navigate } = pageNavSlice.actions;
export default pageNavSlice.reducer;

