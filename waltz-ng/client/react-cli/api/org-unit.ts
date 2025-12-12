import {orgUnitPath} from "../constants/path";

import {fetchJSON} from "./api";
import {OrgUnitType} from "../types/OrgUnit";

const getById = (id: number) => ({
    queryKey: ["orgUnit", "getById", id],
    queryFn: async (): Promise<OrgUnitType> => {
        return await fetchJSON(orgUnitPath.getById(id));
    }
});

const loadAll = () => ({
    queryKey: ["orgUnit", "loadAll"],
    queryFn: async (): Promise<OrgUnitType[]> => {
        return await fetchJSON(orgUnitPath.loadAll());
    }
});

export const orgUnitApi = {
    getById,
    loadAll
};
