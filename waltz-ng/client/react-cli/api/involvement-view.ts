import {involvementViewPath} from "../constants/path";
import {fetchJSON} from "./api";
import {EntityReference} from "../types/Entity";
import {
    InvolvementDetailType,
    InvolvementByDirectionType,
    InvolvementPersonType
} from "../types/Involvement";

const findKeyInvolvementsForEntity = (entityRef: EntityReference) => ({
    queryKey: ["involvement-view", "findKeyInvolvementsForEntity", entityRef],
    queryFn: () => fetchJSON<InvolvementDetailType[]>(involvementViewPath.findKeyInvolvementsForEntity(entityRef))
})

const findAllInvolvementsByEmployeeId = (employeeId: number) => ({
    queryKey: ["involvement-view", "findAllInvolvementsByEmployeeId", employeeId],
    queryFn: () => fetchJSON<InvolvementPersonType>(involvementViewPath.findAllInvolvementsByEmployeeId(employeeId))

})

const findAllInvolvementsForEntityByDirection = (entityRef: EntityReference) => ({
    queryKey: ["involvement-view", "findAllInvolvementsForEntityByDirection", entityRef],
    queryFn: () => fetchJSON<InvolvementByDirectionType>(involvementViewPath.findAllInvolvementsForEntityByDirection(entityRef))
})


export const involvementApi = {
    findAllInvolvementsByEmployeeId,
    findKeyInvolvementsForEntity,
    findAllInvolvementsForEntityByDirection
}