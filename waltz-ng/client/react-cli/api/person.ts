import {baseApiUrl, fetchJSONList} from "./api";
import {Person} from "../types/Person";

const personBaseUrl = `${baseApiUrl}/person`;
const getSelfUrl = `${personBaseUrl}/self`;

const getSelf = () => ({
    queryKey: ['person', 'getSelf'],
    queryFn: async (): Promise<Person> => {
        return await fetchJSONList(getSelfUrl);
    }
})

export default {
    getSelf
}