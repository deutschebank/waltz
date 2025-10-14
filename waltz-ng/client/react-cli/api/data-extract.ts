import {fetchResponse} from "./api";
import {RestMethod} from "../types/Http";
import {DataExtractFileType} from "../types/DataExtract";
import {DataExtractFileTypeEnum} from "../enums/DataExtract";

const dataExtractBaseUrl = `/data-extract`;

/**
 *
 * @param url
 * a url that will be automatically prefixed by
 *
 * `api/data-extract/`
 *
 * i.e. if url = actor/all then within the function it would become
 *
 * `api/data-extract/actor/all`
 * @param method
 * @param format
 * @param filename
 * @param requestBody
 */

export const extract = (url: string,
                        method: RestMethod = "GET",
                        format: DataExtractFileType = DataExtractFileTypeEnum.CSV,
                        filename: string,
                        requestBody?: object) => ({
    queryKey: ['data-extract', 'extract', url, method, format, filename, requestBody],
    queryFn: async() => {
        return await fetchResponse(`${dataExtractBaseUrl}/${url}?format=${format}`, method, requestBody);
    }
});

export default {
    extract
}