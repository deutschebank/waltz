import reduxStore from "../../redux-store";
import {addToast} from "../../redux-slices/toast-slice";
import {ToastCreateType} from "../types/Toast";

/**
 * usage:
 * `mkToast("SUCCESS", "this is a toast")`
 * @param type - type of the toast
 * @param message - content of the toast
 */
export function mkToast({type, message}: ToastCreateType){
    reduxStore.dispatch(addToast({type, message}));
}