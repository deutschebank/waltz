import {applicationPath} from "../constants/path";
import {ApplicationType} from "../types/Application";
import {fetchJSON} from "./api";

const getById = (id: number) => ({
    queryKey: ['app', 'getById', String(id)],
    queryFn: async(): Promise<ApplicationType> => {
        return await fetchJSON(applicationPath.getById(id));
    }
})

export const applicationApi = {
    getById
}