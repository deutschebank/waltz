import {baseApiUrl} from "./api";
import {RestMethod} from "../types/Http";

const dataExtractBaseUrl = `${baseApiUrl}/data-extract`;

/**
 *
 * @param url
 * a url that will be automatically prefixed by
 *
 * `api/data-extract/`
 *
 * i.e. if url = logical-flow then within the function it would become
 *
 * `api/data-extract/logical-flow`
 * @param method
 * rest api method
 */

export const extract = (url: string, method: RestMethod = "GET") => {
    return `${dataExtractBaseUrl}/${url}`;
}