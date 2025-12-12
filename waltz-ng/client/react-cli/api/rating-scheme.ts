import {ratingSchemePath} from "../constants/path";
import {fetchJSON} from "./api";
import {RatingSchemeType} from "../types/Rating";

const findAll = () => ({
    queryKey: ["rating-scheme", "findAll"],
    queryFn: async() => await fetchJSON<RatingSchemeType[]>(ratingSchemePath.findAll())
})

const getById = (id: number) => ({
    queryKey: ["rating-scheme", "getById", id],
    queryFn: async() => await fetchJSON<RatingSchemeType>(ratingSchemePath.getById(id))
})

export const ratingSchemeApi = {
    findAll,
    getById
}
