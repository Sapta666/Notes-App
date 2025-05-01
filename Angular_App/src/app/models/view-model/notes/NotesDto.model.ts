export interface NotesDto {
    Notes_PKey: string;
    User_Pkey: string;
    Title: string;
    Body: string;
    Create_numdate: number;
    Create_numtime: number;
    Lastmod_numdate: number;
    Lastmod_dectime: number;
}

export function getNotesInstance(): NotesDto {
    return {
        Notes_PKey: "",
        User_Pkey: "",
        Title: "",
        Body: "",
        Create_numdate: 0,
        Create_numtime: 0,
        Lastmod_numdate: 0,
        Lastmod_dectime: 0,
    }
}