export interface BasicResponseDto {
    Status: string;
    Message: string;
}

export function getBasicResponseInstance(): BasicResponseDto {
    return {
        Status: "",
        Message: "",
    }
}