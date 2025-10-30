import _ from 'lodash';
import  { fetchJSON } from './api';
import {CostKind} from "../types/Grid";
import {costKindPath} from "../constants/path";
import {allocationSchemePath} from "../constants/path";

export type AllocationScheme = {
    id: string | number;
    name: string,
    description : string,
};

export const findAll = () => ({
    queryKey: ["allocation-scheme"],
    queryFn: async (): Promise<AllocationScheme[]> => {
        return await fetchJSON<AllocationScheme[]>(allocationSchemePath.findAll());
    },
});
