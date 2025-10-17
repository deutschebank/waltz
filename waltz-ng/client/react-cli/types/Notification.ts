import {NotificationTypeEnum} from "../enums/Notification";
import {RGBColor} from "d3-color";

export type NotificationType = keyof typeof NotificationTypeEnum;

export type NotificationConfig = {
    iconName: string;
    color: RGBColor;
}