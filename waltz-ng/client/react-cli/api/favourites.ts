import {favouritesPath} from "../constants/path";
import {EntityReference} from "../types/Entity";
import {IsReadOnlyProvider, ProvenanceProvider} from "../types/Providers";
import {fetchJSON} from "./api";
import {AppGroupEntry} from "../types/AppGroup";

const getFavouritesGroupEntries = () => ({
    queryKey: ['favourites', 'getFavouritesGroupEntries'],
    queryFn: async (): Promise<AppGroupEntry[]> => {
        return await fetchJSON(favouritesPath.getFavouritesGroupEntries());
    }
});

const addApplication = (applicationId: number) => ({
    queryKey: ['favourites', 'addApplication', applicationId],
    queryFn: async (): Promise<AppGroupEntry[]> => {
        return await fetchJSON(favouritesPath.addApplication(applicationId), "POST");
    }
});

const removeApplication = (applicationId: number) => ({
    queryKey: ['favourites', 'removeApplication', applicationId],
    queryFn: async (): Promise<AppGroupEntry[]> => {
        return await fetchJSON(favouritesPath.removeApplication(applicationId), "DELETE");
    }
});

export const favouritesApi = {
    getFavouritesGroupEntries,
    addApplication,
    removeApplication
}