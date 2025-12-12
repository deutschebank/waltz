import {appGroupPath} from "../constants/path";
import {AppGroupType} from "../types/AppGroup";
import {fetchJSON} from "./api";
import {EntityReference} from "../types/Entity";

const findRelatedByEntityRef = (ref: EntityReference) => ({
    queryKey: ['appGroup', 'findRelatedByEntityRef', ref.kind, ref.id],
    queryFn: async (): Promise<AppGroupType[]> => {
        return await fetchJSON(appGroupPath.findRelatedByEntityRef(ref));
    }
});

export const appGroupApi = {
    findRelatedByEntityRef
};
