import {baseApiUrl, fetchJSON} from "./api";
import {Actor} from "../types/Actor";

const actorBaseUrl = `${baseApiUrl}/actor`;
const getByIdPath = (id: number) => `${actorBaseUrl}/id/${id}`;

const findAll = () => ({
    queryKey: ['actor', 'findAllActors'],
    queryFn: async(): Promise<Actor[]> => {
        return await fetchJSON(actorBaseUrl);
    }
});

const getById = (id: number) => ({
    queryKey: ['actor', 'getById', id],
    queryFn: async(): Promise<Actor> => {
        return await fetchJSON(getByIdPath(id));
    }
});

export default {
    findAll,
    getById
}