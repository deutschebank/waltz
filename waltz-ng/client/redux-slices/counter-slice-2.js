import {createSlice} from "@reduxjs/toolkit";

const counterSlice2 = createSlice({
    name: 'counter2',
    initialState: { value: 0 },
    reducers: {
        incremented2: state => { state.value += 1 },
    }
});

export const {incremented2} = counterSlice2.actions;
export default counterSlice2.reducer;