import React, {useCallback, useMemo, useState} from "react";
import Flatpickr from "react-flatpickr";
import "flatpickr/dist/themes/airbnb.css";
import {Options} from "flatpickr/dist/types/options";
import styles from "./DatePicker.module.css";
// Assuming an Icon component exists in React with a similar API
import Icon from "./Icon";
import Button from "./button/Button";

const Modes = {
    VIEW: "VIEW",
    EDIT: "EDIT"
} as const;

type Mode = typeof Modes[keyof typeof Modes];

const formatDate = (d: Date): string => {
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, "0");
    const day = String(d.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
};

export interface DatePickerProps {
    origDate?: Date;
    options?: Partial<Options>;
    canEdit?: boolean;
    onChange?: (newDate: Date) => void;
    className?:string;
    inputClassName?:string;
}

const DatePicker: React.FC<DatePickerProps> = (
    {
        origDate = new Date(),
        options = {},
        canEdit = false,
        onChange,
        className,
    inputClassName
    }) => {

    const [mode, setMode] = useState<Mode>(Modes.VIEW);
    const [originalDate] = useState<Date>(() => new Date(origDate));
    const [value, setValue] = useState<Date>(() => new Date(originalDate));

    const handleSubmit = useCallback((newDate: Date) => {
        if (originalDate.getTime() !== newDate.getTime()) {
            onChange?.(newDate);
        }
    }, [onChange, originalDate]);

    const onCancel = useCallback(() => {
        setValue(new Date(originalDate));
        setMode(Modes.VIEW);
    }, [originalDate]);

    const onEdit = useCallback(() => {
        setMode(Modes.EDIT);
    }, []);

    const mergedOptions: Options = useMemo(() => {
        const defaultOptions: Options = {
            dateFormat: "Y-m-d",
            enableTime: false,
            allowInput: true,
            onChange: (dates) => {
                if (dates[0]) {
                    handleSubmit(dates[0]);
                    setMode(Modes.VIEW);
                }
            }
        };
        return {
            ...defaultOptions,
            ...options
        };
    }, [options, handleSubmit]);

    const formattedValue = useMemo(() => formatDate(value), [value]);

    return (
        <div className = {[styles.wrapper,className].filter(Boolean).join(" ")}>
            {mode === Modes.VIEW
                ? (
                    <>
                        <span className={styles.readonly}>{formattedValue}</span>
                        {canEdit && (
                            <Button className={styles.inlineBtn}
                                    label={<><Icon name="edit"/></>}
                                    onClick={onEdit}
                            />

                        )}
                    </>
                ) : (
                    <>
                        <Flatpickr
                            className={[styles.input,inputClassName].filter(Boolean).join(" ")}
                            options={mergedOptions} value={value} onChange={([d]: Date[]) => setValue(d)}/>
                        <Button className={styles.inlineBtn}
                                label={<><Icon name="times"/></>}
                                onClick={onCancel}
                        />
                    </>
                )}
        </div>
    );
};

export default DatePicker;
