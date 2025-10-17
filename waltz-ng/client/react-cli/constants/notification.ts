import {amberBg, blueBg, greenBg, redBg} from "../../common/colors";
import {NotificationType, NotificationConfig} from "../types/Notification";

export const NOTIFICATION_TYPES: Partial<Record<NotificationType, NotificationConfig>> = {
    SUCCESS: {
        iconName: "check",
        color: greenBg
    } ,
    WARNING: {
        iconName: "exclamation",
        color: amberBg
    },
    ERROR: {
        iconName: "exclamation",
        color: redBg
    },
    INFO: {
        iconName: "info",
        color: blueBg
    }
}