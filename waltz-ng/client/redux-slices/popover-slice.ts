import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { uniqueId } from "lodash";

/**
 * Manages the state for a single, application-wide popover.
 * Creates popover using the following parameters
 * ```
 * {
 *     component: optional svelte component to render
 *     props: optional props to pass to svelte component
 *     content: optional html to render
 *     title: optional title for the popover
 * }
 * ```
 *
 * it is expected that one of `component` or `content` is provides
 * @param popover
 */

// Defines the data structure for a popover.
interface PopoverData {
  id?: string | number;
  dismiss?: () => void;
  title?: string | null;
  content?: string | null;
  component?: React.ElementType | null;
  props?: any;
}

// Defines the shape of the popover slice's state.
interface PopoverState {
  // Holds the data for the currently active popover, or null if none.
  current: PopoverData | null;
}

// Sets the initial state for the popover slice.
const initialState: PopoverState = {
  current: null,
};

// Creates a Redux slice to manage popover state and actions.
const popoverSlice = createSlice({
  name: "popover",
  initialState,
  reducers: {
    // Reducer to create and display a new popover.
    addPopover: (state, action: PayloadAction<PopoverData>) => {
      const id = uniqueId();
      // Setup some sensible defaults for a popover.
      state.current = {
        id,
        ...action.payload,
      };
    },
    // Reducer to dismiss the currently active popover.
    dismissPopover: (state) => {
      state.current = null;
    },
  },
});

// Exports the action creators for the popover slice.
export const { addPopover, dismissPopover } = popoverSlice.actions;
// Exports the reducer function for the popover slice.
export default popoverSlice.reducer;