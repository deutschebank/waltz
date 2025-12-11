import {isEmpty, isString} from "lodash";
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

export function execute<T>(url: string, method: RestMethod = "GET", data?: object): Promise<T>{
    return fetch(url, {
        method: method,
        headers: headers,
        body: isString(data) ? data : JSON.stringify(data)
    }).then(handleResponse);
}

function handleResponse(response: any) {
    return response.text().then((text:any) => {
        const data = text && JSON.parse(text);

        if (!response.ok) {
            const error = (data && data.message) || response.statusText;
            return Promise.reject({error, response, text});
        }

        return { data };
    });
}