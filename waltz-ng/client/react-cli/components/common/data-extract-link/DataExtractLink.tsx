import React, { useState } from "react";
import _ from "lodash";
import Icon from "../Icon";
import toasts from "../../../../svelte-stores/toast-store";
import {displayError} from "../../../../common/error-utils";
import {downloadFile} from "../../../../common/file-utils";
import {$http as http} from "../../../../common/WaltzHttp";
import {useQuery} from "@tanstack/react-query";
import styles from "./DataExtractLink.module.scss";

interface DataExtractLinkProps {
    format?: string | null;
    name?: string;
    method?: "GET" | "POST" | "PUT";
    styling?: "button" | "link";
    filename?: string;
    requestBody?: any | null;
    extractUrl: string;
}

const base = "data-extract";

function mkRequestOptions(method: string, format: string | null, requestBody: any | null) {
    switch (method) {
        case "GET":
            return { method };
        case "POST":
        case "PUT":
            return { method, body: JSON.stringify(requestBody) };
        default:
            throw `Unsupported extract method: ${method}`;
    }
}

function mkFilename(filename: string, fmt: string) {
    return `${filename}.${_.toLower(fmt)}`;
}

const DataExtractLink: React.FC<DataExtractLinkProps> = ({
    format = null,
    name = "Export",
    method = "GET",
    styling = "button",
    filename = "extract",
    requestBody = null,
    extractUrl,
}) => {
    const [extracting, setExtracting] = useState(false);

    const url = `${base}/${extractUrl}`;

    const calcClasses = (styling: "button" | "link" = "button") => {
        switch (styling) {
            case "link":
                return ["btn-link"];
            default:
                return ["btn", "btn-xs", "btn-default"];
        }
    };

    const classes = calcClasses(styling);

    const invokeExport = (fmt: string) => {
        const options: any = {
            params: { format: fmt },
        };
        if (fmt === "XLSX") {
            options.responseType = "arraybuffer";
        }

        const requestOptions = mkRequestOptions(method, format, requestBody);

        return http
            .doFetch(`${url}?format=${fmt}`, requestOptions)
            .then((r: any) => (fmt === "XLSX" ? r.blob() : r.text()))
            .then((data: any) => downloadFile(data, mkFilename(filename, fmt), fmt));
    };

    const doExport = (format: string) => {
        toasts.info("Exporting data");
        setExtracting(true);
        invokeExport(format)
            .then(() => toasts.success("Data exported"))
            .catch((e: any) => displayError("Data export failure", e))
            .finally(() => setExtracting(false));
    };

    const exportAs = (fmt: string) => {
        doExport(fmt);
    };

    const exportCsv = () => {
        doExport("CSV");
    };

    const exportXlsx = () => {
        doExport("XLSX");
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
                            <button className="btn-link" onMouseDown={(e) => { e.preventDefault(); exportCsv(); }}>
                                <Icon name="file-text-o" />
                                Export as csv
                            </button>
                        </li>
                        <li role="menuitem">
                            <button className="btn-link" onClick={(e) => { e.preventDefault(); exportXlsx(); }}>
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
            <button className={classes.join(" ")} onClick={() => exportAs(format)}>
                <Icon name={extracting ? "refresh" : "cloud-download"} spin={extracting} />
                {name}
            </button>
        );
    }
};

export default DataExtractLink;
