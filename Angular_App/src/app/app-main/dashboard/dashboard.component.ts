import { AfterViewInit, Component, HostListener } from '@angular/core';
import { AddEditEnum } from '../../common/enums/add-edit.enum';
import { Router } from '@angular/router';
import { NavigationPageEnum } from '../../common/enums/navigation-page.enum';
import { NotesService } from '../../services/notes.service';
import { getNotesInstance, NotesDto } from '../../models/view-model/notes/NotesDto.model';
import { AppSettingsService } from 'src/app/common/services/app-settings.service';
import { AppSettingsDto } from 'src/app/common/models/AppSettingsDto.model';
import { SuccessFailedEnum } from 'src/app/common/enums/success-failed.enum';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements AfterViewInit {

  //#region Variables

  private _appSettings: AppSettingsDto;

  private _notesList: NotesDto[] = [];  

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
    private _appSettingsService: AppSettingsService,
    private _notesService: NotesService,
  ) {
    this._appSettings = this._appSettingsService.getAppSettings();
  }

  ngAfterViewInit(): void {
    this.listHeight = window.innerHeight - 200;
    this.breakpointActivated = window.innerWidth < 768;

    this.getNoteList();
  }

  @HostListener('window:resize', ['$event'])
  onResize(): void {
    this.listHeight = window.innerHeight - 200;
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
    this._router.navigate([`${NavigationPageEnum.AddEditNote}/${addEdit}`], { queryParams: { qsNote_PKey: this.currentNote?.Notes_PKey } });
  }

  protected onNoteClick(note: NotesDto): void {
    this.currentNote = note;
  }

  protected onDeleteNoteClick(): void {
    this.deleteNote()
  }

  //#endregion

  //#region Api Functions

  private getNoteList(): void {
    this.currentNote = null;

    this._notesService
    .getUserNotesList(this._appSettings.Cnct_PKey)
    .subscribe(response => {
      if(response?.Status?.toUpperCase() == "SUCCESS") {
        this.notesList = response?.Data;
        this._notesList = response?.Data;
      } else
        alert("Failed to get user notes list. "+response?.Message);
    });
  }

  private deleteNote(): void {
    this._notesService
      .deleteNote(this.currentNote.Notes_PKey)
      .subscribe(response => {
        if(response?.Status == SuccessFailedEnum.SUCCESS) 
          this.getNoteList();
        else  
          alert("Failed to delete note. "+response?.Message);
      });
  }

  //#endregion

}
