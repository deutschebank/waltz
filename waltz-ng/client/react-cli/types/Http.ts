export enum RestMethodEnum {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    PATCH = "PATCH",
    DELETE = "DELETE"
}

export type RestMethod = keyof typeof RestMethodEnum;