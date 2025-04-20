import { AfterViewInit, Component, HostListener } from '@angular/core';
import { AddEditEnum } from '../../common/add-edit.enum';
import { Router } from '@angular/router';
import { NavigationPageEnum } from '../../common/navigation-page.enum';
import { NotesService } from '../../services/notes.service';
import { getNotesInstance, NotesDto } from '../../models/view-model/notes/NotesDto.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements AfterViewInit {

  //#region Variables

  protected _notesList: NotesDto[] = [];  

  protected notesList: NotesDto[] = [];  
  protected currentNote: NotesDto = { ...getNotesInstance() };
  protected listHeight: number = 100;
  protected addEditEnum: typeof AddEditEnum = AddEditEnum;

  protected searchText: string = "";
  protected breakpointActivated: boolean = false; // similar to bootstrap col-md

  //#endregion

  //#region Page Load

  constructor(
    private _router: Router,
    private _notesService: NotesService,
  ) {

  }

  ngAfterViewInit(): void {
    this.listHeight = window.innerHeight - 363;
    this.breakpointActivated = window.innerWidth < 768;

    this.getNoteList();
  }

  @HostListener('resize', ['$event'])
  onResize(): void {
    this.listHeight = window.innerHeight - 363;
    this.breakpointActivated = window.innerWidth < 768;
  }

  //#endregion

  //#region Component Functions

  protected onSearchTextInput(): void {
    this.notesList = this._notesList.filter(item => item.Title.includes(this.searchText) || item.Body.includes(this.searchText));
  }

  protected onBackClick(): void {
    this.currentNote = { 
      ...getNotesInstance(),
    };
  }

  protected onAddEditNoteClick(addEdit: AddEditEnum): void {
    this._router.navigate([`${NavigationPageEnum.AddEditNote}/${addEdit}`], { queryParams: { qsNote_PKey: this.currentNote?.Note_PKey } });
  }

  protected onNoteClick(note: NotesDto): void {
    this.currentNote = note;
  }

  protected onDeleteNoteClick(): void {
    this._notesService.deleteNote(this.currentNote.Note_PKey);
    this.currentNote = null;
    this.getNoteList();
  }

  //#endregion

  //#region Service Functions

  private getNoteList(): void {
    this._notesList = this._notesService.getNoteList();    
    this.notesList = this._notesService.getNoteList();
  }

  //#endregion

}
