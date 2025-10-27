import {fetchJSON} from "./api";
import {Actor} from "../types/Actor";
import {actorPath} from "../constants/path";

const findAll = () => ({
    queryKey: ['actor', 'findAllActors'],
    queryFn: async(): Promise<Actor[]> => {
        return await fetchJSON(actorPath.findAll());
    }
});

const getById = (id: number) => ({
    queryKey: ['actor', 'getById', id],
    queryFn: async(): Promise<Actor> => {
        return await fetchJSON(actorPath.getById(id));
    }
});

export default {
    findAll,
    getById
}