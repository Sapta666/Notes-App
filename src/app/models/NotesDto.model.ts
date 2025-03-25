export interface NotesDto {
    Note_PKey: string;
    Title: string;
    Body: string;
}

export function getNotesInstance(): NotesDto {
    return {
        Note_PKey: "",
        Title: "",
        Body: "",
    }
}