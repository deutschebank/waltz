import _ from "lodash";
import get from 'lodash/get'

export type HeatInput = {
    date: string;
    count: number;

}

export type DayCell = {
    date: Date;
    value: number;

}

export type MonthBlock = {
    startDate: Date;
    days: DayCell[];
}


export const monthNames : string[] = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

export const dimensions = {
    diagram: {width:2400,height:800},
    day: {width:20},
    month: {width:150,height:160},
    circleRadius: 8,
    weekPadding: 10,
    monthsPerline: 6,
};

export function mkDateKeyFromComponents(month:number, day:number, year:number) {
    return year * 10000 + month * 100 + day;
}

function mkDateKeyFromDateStr(dateStr:number) {
    const date = new Date(dateStr);
    return mkDateKeyFromComponents(date.getMonth() + 1, date.getDate(), date.getFullYear());
}


function toDateFromDateKey(dateKey:number) {
    let year = Math.floor(dateKey / 10000);
    let month = Math.floor((dateKey % 10000) / 100);
    let date = Math.floor(dateKey % 100);
    return new Date(year, month - 1, date);
}

export function daysInMonth(month:number, year:number) {
    return new Date(year, month, 0).getDate();
}


function mkDaysForMonth(data:HeatInput[], date:Date):DayCell[] {

    const month = date.getMonth() + 1;
    const year = date.getFullYear();
    const dayCount = daysInMonth(month, year);

    const dataByDate = _.keyBy(data, (d: { date: number; }) => mkDateKeyFromDateStr(d.date));

    return _.map(_.range(dayCount), (x: number) => {

        const day = x + 1;

        const dateKey = mkDateKeyFromComponents(month, day, year);

        const value = get(dataByDate, [dateKey, "count"], 0) as number;

        return {date: toDateFromDateKey(dateKey), value}
    });
}

export const prepareMonthData = (
    data:HeatInput[] = [],
    startDate:Date,
    endDate:Date ) :MonthBlock[] => {

    let initialCalendarDate = new Date(startDate.getFullYear(),startDate.getMonth(),1)
    const months:MonthBlock[] = [];

    while (initialCalendarDate < endDate) {
        const date:Date  = new Date(initialCalendarDate);

        const month = {
            startDate: date,
            days: mkDaysForMonth(data, date)
        }

        months.push(month);
        initialCalendarDate = new Date(initialCalendarDate.setMonth(initialCalendarDate.getMonth() + 1))
    }

    return months
}














// export const dimensions = {
//     diagram: {
//         width: 2400,
//         height: 800
//     },
//     day: {
//         width: 20
//     },
//     month: {
//         width: 150,
//         height: 160
//     },
//     circleRadius: 8,
//     weekPadding: 10,
//     monthsPerLine: 6
// }