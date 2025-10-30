import  { fetchJSON } from './api';
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
