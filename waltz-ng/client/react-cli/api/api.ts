import _ from "lodash";
import {RestMethod} from "../types/Http";
import {objects} from "../../process-diagram/components/process-diagram/diagram-store";

export const baseApiUrl = "api";
export const headers: HeadersInit = {
    "Content-Type": "application/json"
}

if (typeof window !== 'undefined') {
    const satellizerToken = localStorage.getItem("satellizer_token");

    if (!_.isEmpty(satellizerToken)) {
        headers["Authorization"] = `Bearer ${satellizerToken}`;
    }
}

export async function fetchJSONList<T>(url: string, method: RestMethod = "GET", data?: object): Promise<T>{
    return await fetch(url, {
        method: method,
        headers: headers,
        body: data ? JSON.stringify(data) : null
    })
    .then(res => res.json());
}