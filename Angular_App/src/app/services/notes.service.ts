import { Injectable, OnInit } from "@angular/core";
import { NotesDto } from "../models/NotesDto.model";
import { AddEditNoteDto } from "../models/AddEditNoteDto.model";

@Injectable({
    providedIn: 'root'
})
export class NotesService {

    //#region Variables

    private _notesList: NotesDto[] = [];

    //#endregion

    //#region Page Load

    constructor() {
        this._notesList = JSON.parse(sessionStorage.getItem("Notes_Data")) ?? [];
    }

    //#endregion

    //#region Public Functions    

    public getNoteList(): NotesDto[] {
        return this._notesList;
    } 

    public getNoteInfo(note_PKey: string): NotesDto {          
        return this._notesList.find(item => item.Note_PKey == note_PKey);
    }

    public addNote(addEditNote: AddEditNoteDto): void {
        let note: NotesDto = {
            Note_PKey: (new Date()).getTime()?.toString(),
            Title: addEditNote.Title,
            Body: addEditNote.Body, 
        };

        this._notesList.push(note);

        this.saveSessionData();
    }

    public editNote(addEditNote: AddEditNoteDto): void {
        let note: NotesDto = {
            Note_PKey: addEditNote.Note_PKey,
            Title: addEditNote.Title,
            Body: addEditNote.Body, 
        };

        let index: number = this._notesList.findIndex(item => item.Note_PKey == addEditNote.Note_PKey);
        this._notesList[index] = {
            ...note
        };
    }

    public deleteNote(note_PKey: string): void {
        this._notesList = this._notesList.filter(item => {
            return item.Note_PKey != note_PKey;
        });

        if(!this._notesList)
            this._notesList = [];

        this.saveSessionData();
    }

    public getNotesCount(): number {
        return this._notesList?.length;
    }

    //#endregion

    //#region Private Functions

    private saveSessionData(): void {
        sessionStorage.setItem("Notes_Data",JSON.stringify(this._notesList));
    }

    //#endregion

}