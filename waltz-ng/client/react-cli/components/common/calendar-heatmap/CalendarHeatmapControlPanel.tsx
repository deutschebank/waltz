import React, { useState,ChangeEvent,MouseEvent ,useMemo} from "react";
import Icon from "../Icon";
import Button from "../button/Button";
import {Options} from "flatpickr/dist/types/options";
import DatePicker from "../DatePicker";
import styles from "./CalendarHeatmapControlPanel.module.css";

type Props = {
    startDate: Date;
    endDate: Date;
    setStartDate: (date:string | Date) => void;
    setEndDate: (date:string | Date ) => void;
    title?:string;
};

const dateOpts:Partial<Options>={
    dateFormat:"Y-m-d",
    enableTime:false,
    allowInput:true,
}

const toDate = (date: Date|string) : Date => date instanceof globalThis.Date ? date : new globalThis.Date(date);

const format = (date:Date) => `${date.getFullYear()}-
${String(date.getMonth()+1).padStart(2,"0")}-
${String(date.getDate()).padStart(2,"0")}`;

const CalendarHeatmapControlPanel: React.FC<Props> = ({
    startDate,
    endDate,
    setStartDate,
    setEndDate,
    title="Date Range"
}) => {

    const [changeDateSelection, setChangeDateSelection] = useState<boolean>(false);
    const start = toDate(startDate);
    const end = toDate(endDate);

    const handleStartChange = (e: ChangeEvent<HTMLInputElement>): void => {
        setStartDate(new Date(e.target.value));
    }

    const handleEndChange = (e: ChangeEvent<HTMLInputElement>): void => {
        setStartDate(new Date(e.target.value));
    }

    const toggleChangeSelection = (e: MouseEvent<HTMLButtonElement>): void => {
        e.preventDefault();
        setChangeDateSelection((prev) => !prev)
    };

    const rangeLabel = useMemo(() => `${format(startDate)} -> ${format(endDate)}`, [startDate, endDate]);
    const [isEditing, setIsEditing] = useState(false);
    return (
        <section className={styles.panel} aria-label="Calendar date range controls">
            {/*Display row*/}
            <div className={styles.rangeCard} role={"group"} aria-label="Date-range">
                <span className={styles.label}>Date range</span>
                <span className={styles.value}>{rangeLabel}</span>

                <Button label={<><Icon name={isEditing ? "times" : "pencil"}></Icon></>}
                        onClick={() => setIsEditing(v => !v)}
                        aria-label={isEditing ? "Close Data range editor" : "Change date range"}
                />
            </div>

            {isEditing && (
                <div className={styles.editorRow}>
                    <div className={styles.picker}>
                        <span className={styles.pickerLabel}>Start</span>
                        <DatePicker
                            className={styles.pickerInput}
                            origDate={startDate}
                            options={dateOpts}
                            canEdit={true}
                            onChange={(d: Date) => setStartDate(d)}
                        />
                    </div>
                    <div className={styles.to}>to</div>

                    <div className={styles.picker}>
                        <span className={styles.pickerLabel}>End</span>
                        <DatePicker
                            className={styles.pickerInput}
                            origDate={endDate}
                            options={dateOpts}
                            canEdit={true}
                            onChange={(d: Date) => setEndDate(d)}
                        />
                    </div>

                    <div className={styles.actions}>
                        <Button className={styles.primaryBtn}
                                label={<>Apply</>}
                                onClick={() => setIsEditing(false)}
                        />
                        <Button className={styles.ghostBtn}
                                label={<>Cancel</>}
                                onClick={() => setIsEditing(false)}
                        />
                    </div>

                </div>
            )}
        </section>
    );
};


        {/*// <div className = {styles.wrapper}>*/}
        {/*//     {changeDateSelection?(*/}
        {/*//         <>*/}
        {/*//             <div className={styles.row}>*/}
        {/*//                 <div className="col-sm-6">*/}
        {/*//                     <strong>Start Date:</strong>*/}
        {/*//                     /!*<input type="date" value={format(start)} onChange={handleStartChange}/>*!/*/}
        {/*//                     <DatePicker*/}
        {/*//                             origDate={startDate}*/}
        {/*//                             options={dateOpts}*/}
        {/*//                             canEdit={true}*/}
        {/*//                             onChange={(d:Date) => setStartDate(d)}/>*/}
        {/*//                 </div>*/}
        {/*//                 <div className="col-sm-6">*/}
        {/*//                     <strong>End Date:</strong>*/}
        {/*//                     /!*<input type="date" value={format(end)} onChange={handleEndChange}/>*!/*/}
        {/*//                     <DatePicker*/}
        {/*//                         origDate={startDate}*/}
        {/*//                         options={dateOpts}*/}
        {/*//                         canEdit={true}*/}
        {/*//                         onChange={(d:Date) => setEndDate(d)}/>*/}
        {/*//                 </div>*/}
        {/*//             </div>*/}
        {/*//             <div className="row">*/}
        {/*//                 <div className="col-sm-12">*/}
        {/*//                     <Button className = "btnbtn-skinny"*/}
        {/*//                             onClick = {toggleChangeSelection}*/}
        {/*//                             label={<>Close*/}
        {/*//                                 <Icon name="times"/></>}*/}
        {/*//                     />*/}
        {/*//*/}
        {/*//*/}
        {/*//                 </div>*/}
        {/*//             </div>*/}
        {/*//         </>*/}
        {/*//     ):(*/}
        {/*//         <>*/}
        {/*//             <b>Date Range:</b> {format(start)} to {format(end)}.{" "}*/}
        {/*//             <Button className = "btn btn-skinny"*/}
        {/*//                     onClick = {toggleChangeSelection}*/}
        {/*//                     label={<>Change Date Range*/}
        {/*//                         <Icon name="pencil"/></>}*/}
        {/*//             />*/}
        {/*//         </>*/}
        {/*//     )}*/}
        {/*// </div>*/}
    // )};

export default CalendarHeatmapControlPanel;