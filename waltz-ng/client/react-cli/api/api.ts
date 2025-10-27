import {isEmpty} from "lodash";
import {RestMethod} from "../types/Http";

export const headers: HeadersInit = {
    "Content-Type": "application/json"
}

if (typeof window !== 'undefined') {
    const satellizerToken = localStorage.getItem("satellizer_token");

    if (!isEmpty(satellizerToken)) {
        headers["Authorization"] = `Bearer ${satellizerToken}`;
    }
}

export async function fetchJSON<T>(url: string, method: RestMethod = "GET", data?: object): Promise<T>{
    return await fetch(url, {
        method: method,
        headers: headers,
        body: data ? JSON.stringify(data) : null
    })
    .then(res => res.json());
}

export async function fetchResponse(url: string, method: RestMethod = "GET", data?: object): Promise<Response>{
    return await fetch(url, {
        method: method,
        headers: headers,
        body: data ? JSON.stringify(data) : null
    });
}