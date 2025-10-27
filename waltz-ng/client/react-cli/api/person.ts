import {fetchJSON} from "./api";
import {Person} from "../types/Person";
import {personPath} from "../constants/path";

const getSelf = () => ({
    queryKey: ['person', 'getSelf'],
    queryFn: async (): Promise<Person> => {
        return await fetchJSON(personPath.getSelf());
    }
})

export default {
    getSelf
}