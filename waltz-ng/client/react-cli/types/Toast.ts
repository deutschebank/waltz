import {NotificationType} from "./Notification";

export type ToastType = {
    id: string,
    type: NotificationType,
    message: string
}

export type ToastCreateType = {
    type: NotificationType,
    message: string
}