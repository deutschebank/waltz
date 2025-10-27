import React from "react";
import Icon from "../Icon";
import toasts from "../../../../svelte-stores/toast-store";
import {displayError} from "../../../../common/error-utils";
import {downloadFile} from "../../../../common/file-utils";
import {useMutation} from "@tanstack/react-query";
import styles from "./DataExtractLink.module.scss";
import {RestMethod} from "../../../types/Http";
import {DataExtractFileType} from "../../../types/DataExtract";
import {DataExtractFileTypeEnum} from "../../../enums/DataExtract";
import dataExtractApi from "../../../api/data-extract";

interface DataExtractLinkProps {
    format?: DataExtractFileType
    name?: string;
    method?: RestMethod;
    styling?: "button" | "link";
    filename?: string;
    requestBody?: any | null;
    extractUrl: string;
}

function mkFilename(filename: string, fmt: DataExtractFileType) {
    return `${filename}.${fmt.toLowerCase()}`;
}

const DataExtractLink: React.FC<DataExtractLinkProps> = ({
    format,
    name = "Export",
    method = "GET",
    styling = "button",
    filename = "extract",
    requestBody = null,
    extractUrl
}) => {

    const { isPending: extracting, mutate } = useMutation<Response, Error, DataExtractFileType>({
        mutationFn: (fmt: DataExtractFileType) => {
            const {queryFn} = dataExtractApi.extract(extractUrl, method, fmt, filename, requestBody);
            return queryFn();
        },
        onSuccess: (data, fmt) => {
            invokeExport(data, fmt);
        },
        onError: (e) => displayError("Data export failure", e)
    });

    const calcClasses = (styling: "button" | "link" = "button") => {
        switch (styling) {
            case "link":
                return ["btn-link"];
            default:
                return ["btn", "btn-xs", "btn-default"];
        }
    };

    const classes = calcClasses(styling);

    const invokeExport = (response: Response, fmt: DataExtractFileType) => {
        try {
            if (fmt === DataExtractFileTypeEnum.CSV) {
                response
                    .text()
                    .then(data => downloadFile(data, mkFilename(filename, fmt), fmt))
                    .then(_ => toasts.success("CSV file downloaded"))
            } else {
                response
                    .blob()
                    .then(data => downloadFile(data, mkFilename(filename, fmt), fmt))
                    .then(_ => toasts.success("XLSX file downloaded"))
            }
        } catch (e) {
            displayError("Data export failure", e);
        }
    };

    const doExport = (format: DataExtractFileType) => {
        toasts.info("Exporting data");
        mutate(format);
    };

    if (!format) {
        return (
            <ul className={styles.waltzDataExtract}>
                <li>
                    <button className={classes.join(" ")}>
                        <Icon name={extracting ? "refresh" : "cloud-download"} spin={extracting} />
                        {name}
                        <span className="caret"></span>
                    </button>
                    <ul className={styles.dropdown} role="menu">
                        <li role="menuitem">
                            <button className="btn-link" onClick={(e) => { e.preventDefault(); doExport(DataExtractFileTypeEnum.CSV); }}>
                                <Icon name="file-text-o" />
                                Export as csv
                            </button>
                        </li>
                        <li role="menuitem">
                            <button className="btn-link" onClick={(e) => { e.preventDefault(); doExport(DataExtractFileTypeEnum.XLSX); }}>
                                <Icon name="file-excel-o" />
                                Export as xlsx
                            </button>
                        </li>
                    </ul>
                </li>
            </ul>
        );
    } else {
        return (
            <button className={classes.join(" ")} onClick={() => doExport(format)}>
                <Icon name={extracting ? "refresh" : "cloud-download"} spin={extracting} />
                {name}
            </button>
        );
    }
};

export default DataExtractLink;
