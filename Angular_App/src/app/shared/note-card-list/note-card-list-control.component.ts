import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { NotesService } from '../../services/notes.service';
import { NotesDto } from '../../models/view-model/notes/NotesDto.model';
import { AddEditEnum } from '../../common/enums/add-edit.enum';
import { AppSettingsDto } from 'src/app/common/models/AppSettingsDto.model';
import { AppSettingsService } from 'src/app/common/services/app-settings.service';

@Component({
  selector: 'app-note-card-list-control',
  templateUrl: './note-card-list-control.component.html'
})
export class NoteCardListControlComponent implements AfterViewInit {

  //#region Private Variables

  private _appSettings: AppSettingsDto;

  protected notesList: NotesDto[] = [];

  //#endregion

  //#region Properties

  @Input() pTotalHeight: number = 100;

  @Output() onNavigate = new EventEmitter();

  //#endregion

  //#region Page Load

  constructor(
    private _notesService: NotesService,
    private _appSettingsService: AppSettingsService
  ) {
    this._appSettings = this._appSettingsService.getAppSettings();
  }

  ngAfterViewInit(): void {
    this.getUserNotesList();
    // this.notesList = [
    //   {
    //     Note_PKey: "34",
    //     Title: "Test Note",
    //     Body: "testing"        
    //   },{
    //     Note_PKey: "35",
    //     Title: "Test Note",
    //     Body: "testing"        
    //   }
    // ]
  }

  //#endregion

  //#region Component Functions

  protected onDeleteNoteClick(note: NotesDto): void {
    this.notesList = this.notesList.filter(item => item.Notes_PKey != note.Notes_PKey);
  }

  protected onEditNoteClick(note: NotesDto): void {
    this.onNavigate.emit({option: AddEditEnum.Edit, Note_PKey: note.Notes_PKey});
  }

  protected onAddNoteClick(): void {
    this.onNavigate.emit({option: AddEditEnum.Add});
  }

  //#endregion

  //#region Api Functions

  private getUserNotesList(): void {
    this._notesService
      .getUserNotesList(this._appSettings.Cnct_PKey)
      .subscribe(response => {
        if(response?.Status?.toUpperCase() == "SUCCESS")
          this.notesList = response?.Data;
        else
          alert("Failed to get user notes list. "+response?.Message);
      });
  }

  //#endregion

}
