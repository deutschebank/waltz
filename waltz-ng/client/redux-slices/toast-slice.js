import {createSlice} from "@reduxjs/toolkit";
import {isEqual, uniqueId} from "lodash";

/**
 * If you want to display toasts on some page then use
 * reduxStore.dispatch(addToast({
 *     type: "",
 *     message: ""
 * }));
 */
const toastSlice = createSlice({
    name: 'toasts',
    initialState: [],
    reducers: {
        addToast: (state, action) => {
            state.push({
                id: uniqueId("toast_"),
                ...action.payload
            })
        },
        removeToast: (state, action) => state.filter(t => t.id !== action.payload.id)
    }
});

export const { addToast, removeToast } = toastSlice.actions;
export default toastSlice.reducer;