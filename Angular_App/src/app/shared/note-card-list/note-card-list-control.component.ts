import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { NotesService } from '../../services/notes.service';
import { NotesDto } from '../../models/NotesDto.model';
import { AddEditEnum } from '../../common/add-edit.enum';

@Component({
  selector: 'app-note-card-list-control',
  templateUrl: './note-card-list-control.component.html'
})
export class NoteCardListControlComponent implements AfterViewInit {

  //#region Private Variables

  protected notesList: NotesDto[] = [];

  //#endregion

  //#region Properties

  @Input() pTotalHeight: number = 100;

  @Output() onNavigate = new EventEmitter();

  //#endregion

  //#region Page Load

  constructor(
    private _notesService: NotesService,
  ) {

  }

  ngAfterViewInit(): void {
    this.notesList = this._notesService.getNoteList();
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
    this.notesList = this.notesList.filter(item => item.Note_PKey != note.Note_PKey);
  }

  protected onEditNoteClick(note: NotesDto): void {
    this.onNavigate.emit({option: AddEditEnum.Edit, Note_PKey: note.Note_PKey});
  }

  protected onAddNoteClick(): void {
    this.onNavigate.emit({option: AddEditEnum.Add});
  }

  //#endregion

}
