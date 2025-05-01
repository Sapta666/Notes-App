export interface AddEditNoteDto {
    Notes_PKey: string;
    Title: string;
    Body: string;
}

export function getAddEditNoteInstance(): AddEditNoteDto {
    return {
        Notes_PKey: "",
        Title: "",
        Body: "",
    }
}