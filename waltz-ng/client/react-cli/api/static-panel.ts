import {staticPanelPath} from "../constants/path";
import {fetchJSON} from "./api";
import {StaticPanelType} from "../types/StaticPanel";

const load = () => ({
    queryKey: ["static-panel", "load"],
    queryFn: async(): Promise<StaticPanelType[]> => {
        return await fetchJSON(staticPanelPath.load());
    },
    gcTime: 0 // garbage collection time set to 0 -> force on every call, no caching
});

const save = () => ({
    queryKey: ["static-panel", "save"],
    queryFn: async(panel: Partial<StaticPanelType>): Promise<boolean> => {
        return await fetchJSON(staticPanelPath.save(), "POST", panel);
    },
    gcTime: 0
});

const findByGroupKey = (groupKey: string) => ({
    queryKey: ["static-panel", "findByGroupKey", groupKey],
    queryFn: async(): Promise<StaticPanelType[]> => {
       return await fetchJSON(staticPanelPath.findByGroupKey(groupKey));
    },
    gcTime: 0
});

export const staticPanelApi = {
    load,
    save,
    findByGroupKey
};