import { Injectable, OnInit } from "@angular/core";
import { NotesDto } from "../models/view-model/notes/NotesDto.model";
import { AddEditNoteDto } from "../models/function-model/notes/AddEditNoteDto.model";
import { Observable } from "rxjs";
import { ResponseDataDto } from "../common/models/ResponseDataDto.model";
import { HttpClient } from "@angular/common/http";
import { BasicResponseDto } from "../common/models/BasicResponseDto.model";

@Injectable({
    providedIn: 'root'
})
export class NotesService {

    //#region Variables

    private _notesList: NotesDto[] = [];

    //#endregion

    //#region Page Load

    constructor(
        private _httpClient: HttpClient,
    ) {
        this._notesList = JSON.parse(sessionStorage.getItem("Notes_Data")) ?? [];
    }

    //#endregion

    //#region Public Functions    

    public getUserNotesList(user_PKey: string): Observable<ResponseDataDto<NotesDto[]>> {
        return this._httpClient.get<ResponseDataDto<NotesDto[]>>(`{{baseUrl}}/Notes/list/${user_PKey}`);
    }

    public getNoteInfo(notes_PKey: string): Observable<ResponseDataDto<NotesDto>> {
        return this._httpClient.get<ResponseDataDto<NotesDto>>(`{{baseUrl}}/Notes/${notes_PKey}`);
    }

    public addNote(user_PKey: string,addEditNote: AddEditNoteDto): Observable<BasicResponseDto> {
        return this._httpClient.post<BasicResponseDto>(`{{baseUrl}}/Notes/addNote/${user_PKey}`,addEditNote);
    }

    public updateNote(note_PKey: string,addEditNote: AddEditNoteDto): Observable<BasicResponseDto> {
        return this._httpClient.put<BasicResponseDto>(`{{baseUrl}}/Notes/updateNote/${note_PKey}`,addEditNote);
    }

    public deleteNote(notes_PKey: string): Observable<BasicResponseDto> {
        return this._httpClient.delete<BasicResponseDto>(`{{baseUrl}}/Notes/${notes_PKey}`);
    }

    public getNotesCount(): number {
        return this._notesList?.length;
    }

    //#endregion

    //#region Private Functions

    private saveSessionData(): void {
        sessionStorage.setItem("Notes_Data", JSON.stringify(this._notesList));
    }

    //#endregion

}