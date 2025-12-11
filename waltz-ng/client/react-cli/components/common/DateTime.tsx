import React from 'react';
import moment from 'moment';

interface DateTimeProps {
    dateTime: string | Date;
    relative?: boolean;
    formatStr?: string;
}

const DateTime: React.FC<DateTimeProps> = ({ dateTime, relative = true, formatStr = "YYYY-MM-DD HH:mm:ss" }) => {

    const timeAgo = (t: string | Date) => moment.utc(t).fromNow();
    const format = (t: string | Date) => moment.utc(t).local().format(formatStr);

    if (relative) {
        return (
            <span title={format(dateTime)}>
                {timeAgo(dateTime)}
            </span>
        );
    } else {
        return (
            <span title={timeAgo(dateTime)}>
                {format(dateTime)}
            </span>
        );
    }
};

export default DateTime;
