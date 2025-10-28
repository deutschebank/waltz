import {staticPanelPath} from "../constants/path";
import {fetchJSON} from "./api";

interface StaticPanel {
    content: string,
    encoding: string,
    group: string,
    icon: string,
    id: number,
    priority: 0,
    title: string,
    width: number
}

const load = () => ({
    queryKey: [],
    queryFn: async() => {
        return await fetchJSON(staticPanelPath.load());
    },
    gcTime: 0 // garbage collection time set to 0 -> force on every call, no caching
});

const save = () => ({
    queryKey: [],
    queryFn: async(panel: object) => {
        return await fetchJSON(staticPanelPath.save(), "POST", panel);
    },
    gcTime: 0
});

const findByGroupKey = (groupKey: string) => ({
   queryKey: [],
   queryFn: async(): Promise<StaticPanel[]> => {
       return await fetchJSON(staticPanelPath.findByGroupKey(groupKey));
   },
    gcTime: 0
});

export default {
    load,
    save,
    findByGroupKey
};