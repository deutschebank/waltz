import {createSlice} from "@reduxjs/toolkit";

const pageNavSlice = createSlice({
    name: 'pageNaV',
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

