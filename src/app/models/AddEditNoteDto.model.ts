export interface AddEditNoteDto {
    Note_PKey: string;
    Title: string;
    Body: string;
}

export function getAddEditNoteInstance(): AddEditNoteDto {
    return {
        Note_PKey: "",
        Title: "",
        Body: "",
    }
}