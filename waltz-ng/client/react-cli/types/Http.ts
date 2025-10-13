export enum RestMethodEnum {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE
}

export type RestMethod = keyof typeof RestMethodEnum;