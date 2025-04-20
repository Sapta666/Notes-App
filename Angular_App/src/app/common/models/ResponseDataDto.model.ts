import { BasicResponseDto, getBasicResponseInstance } from "./BasicResponseDto.model";

export interface ResponseDataDto<T> extends BasicResponseDto {
    Data: T;
}

export function getResponseDataInstance<T>(): ResponseDataDto<T> {
    return {
        Data: null,
        ...getBasicResponseInstance()
    }
}