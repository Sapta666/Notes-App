import { AfterViewInit, Component, HostListener } from '@angular/core';
import { AddEditEnum } from '../../common/add-edit.enum';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { getAddEditNoteInstance } from '../../models/function-model/notes/AddEditNoteDto.model';
import { NotesService } from '../../services/notes.service';
import { NotesDto } from '../../models/view-model/notes/NotesDto.model';
import { NavigationPageEnum } from '../../common/navigation-page.enum';

@Component({
  selector: 'app-add-edit-note',
  templateUrl: './add-edit-note.component.html'
})
export class AddEditNoteComponent implements AfterViewInit {

  //#region Variables

  private _note_PKey: string = "";

  protected formGroup: FormGroup;

  protected addEdit: AddEditEnum = AddEditEnum.Add;
  protected addEditEnum: typeof AddEditEnum = AddEditEnum;
  protected listHeight: number = 100;
  protected breakpointActivated: boolean = false;

  //#endregion

  //#region Page Load

  constructor(
    private _activatedRoute: ActivatedRoute,
    private _fb: FormBuilder,
    private _notesService: NotesService,
    private _router: Router,
  ) {
    this.initializeFormGroup();
  }

  ngAfterViewInit(): void {
    this._activatedRoute.params.subscribe(params => {
      this.addEdit = params["AddEdit"];
      this._note_PKey = this._activatedRoute.snapshot.queryParams["qsNote_PKey"];

      if (this.addEdit == AddEditEnum.Edit)
        this.getNoteInfo();
    });

    this.listHeight = window.innerHeight - 363;
    this.breakpointActivated = window.innerWidth < 768;

  }

  @HostListener('window:resize', ['$event'])
  onResize(): void {
    this.listHeight = window.innerHeight - 363;
    this.breakpointActivated = window.innerWidth < 768;    
  }

  //#endregion

  //#region Private Functions

  private initializeFormGroup(): void {
    this.formGroup = this._fb.group({ ...getAddEditNoteInstance() });

    this.formGroup.controls["Title"].setValidators([Validators.required]);
    this.formGroup.controls["Body"].setValidators([Validators.required]);
  }

  private initializeFormGroupValue(noteInfo: NotesDto): void {
    this.formGroup.patchValue({
      ...noteInfo
    })
  }

  private navigateToDashboard(): void {
    this._router.navigate([`${NavigationPageEnum.Dashboard}`]);
  } 

  private validateData(): boolean {
    let errorMessage: string = "";
    try {
      this.formGroup.markAllAsTouched();  
      if (this.formGroup.controls["Title"].value.trim() == "")
        errorMessage += "\n Title is required."
      if (this.formGroup.controls["Body"].value.trim() == "")
        errorMessage += "\n Note body is required."
      if (!this.formGroup.valid || errorMessage.length > 0) {
        alert("Form Validation failed. " + errorMessage);
        return false;
      }

    } catch (e) {
      console.error(e);
      alert("Unkown error occurred. " + errorMessage);
      return false;
    }
    return true;
  }

  //#endregion    

  //#region Component Functions

  protected onCancelClick(): void {
    this.navigateToDashboard();
  }

  protected onSubmitClick(): void {
    if (this.validateData()) {
      if (this.addEdit == AddEditEnum.Add)
        this.addNote();
      else
        this.editNote();

        this.navigateToDashboard();
    }
  }

  //#endregion

  //#region Service Functions

  private getNoteInfo(): void {
    let noteInfo: NotesDto = this._notesService
      .getNoteInfo(this._note_PKey);

    if (noteInfo)
      this.initializeFormGroupValue(noteInfo);
  }

  private addNote(): void {
    this._notesService
      .addNote(this.formGroup.getRawValue());
  }

  private editNote(): void {
    this._notesService
      .editNote(this.formGroup.getRawValue());      
  }

  //#endregion

}
