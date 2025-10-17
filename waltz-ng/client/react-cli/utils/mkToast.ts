import reduxStore from "../../redux-store";
import {addToast} from "../../redux-slices/toast-slice";
import {NotificationType} from "../types/Notification";

export function mkToast(type: NotificationType, message: string){
    reduxStore.dispatch(addToast({type, message}));
}