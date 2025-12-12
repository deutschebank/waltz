import {assessmentDefinitionPath} from "../constants/path";
import {fetchJSON} from "./api";
import {EntityKind, EntityReference} from "../types/Entity";
import {AssessmentDefinition} from "../types/Assessment";

const getById = (id: number) => ({
    queryKey: ["assessment-definition", "getById", id],
    queryFn: async() => await fetchJSON<AssessmentDefinition>(assessmentDefinitionPath.getById(id))
})

const findAll = () => ({
    queryKey: ["assessment-definition", "findAll"],
    queryFn: async() => await fetchJSON<AssessmentDefinition[]>(assessmentDefinitionPath.findAll())
})

const findByKind = (kind: EntityKind) => ({
    queryKey: ["assessment-definition", "findByKind", kind],
    queryFn: async() => await fetchJSON<AssessmentDefinition[]>(assessmentDefinitionPath.findByKind(kind))
})

const findByEntityReference = (ref: EntityReference) => ({
    queryKey: ["assessment-definition", "findByEntityReference", ref],
    queryFn: async() => await fetchJSON<AssessmentDefinition[]>(assessmentDefinitionPath.findByEntityReference(ref))
})

const findFavouritesForUser = () => ({
    queryKey: ["assessment-definition", "findFavouritesForUser"],
    queryFn: async() => await fetchJSON<AssessmentDefinition[]>(assessmentDefinitionPath.findFavouritesForUser())
})

export const assessmentDefinitionApi = {
    getById,
    findAll,
    findByKind,
    findByEntityReference,
    findFavouritesForUser
}
