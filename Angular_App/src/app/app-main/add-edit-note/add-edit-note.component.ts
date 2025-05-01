import { AfterViewInit, Component, HostListener } from '@angular/core';
import { AddEditEnum } from '../../common/enums/add-edit.enum';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { getAddEditNoteInstance } from '../../models/function-model/notes/AddEditNoteDto.model';
import { NotesService } from '../../services/notes.service';
import { NotesDto } from '../../models/view-model/notes/NotesDto.model';
import { NavigationPageEnum } from '../../common/enums/navigation-page.enum';
import { SuccessFailedEnum } from 'src/app/common/enums/success-failed.enum';
import { AppSettingsDto } from 'src/app/common/models/AppSettingsDto.model';
import { AppSettingsService } from 'src/app/common/services/app-settings.service';

@Component({
  selector: 'app-add-edit-note',
  templateUrl: './add-edit-note.component.html'
})
export class AddEditNoteComponent implements AfterViewInit {

  //#region Variables

  private _notes_PKey: string = "";
  private _appSettings: AppSettingsDto = null;

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
    private _appSettingsService: AppSettingsService,
    private _notesService: NotesService,
    private _router: Router,
  ) {
    this._appSettings = this._appSettingsService.getAppSettings();
    this.initializeFormGroup();
  }

  ngAfterViewInit(): void {
    this._activatedRoute.params.subscribe(params => {
      this.addEdit = params["AddEdit"];
      this._notes_PKey = this._activatedRoute.snapshot.queryParams["qsNote_PKey"];

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
    }
  }

  //#endregion

  //#region Service Functions

  private getNoteInfo(): void {
    this._notesService
      .getNoteInfo(this._notes_PKey)
      .subscribe(response => {
        if(response?.Status == SuccessFailedEnum.SUCCESS)
          this.initializeFormGroupValue(response?.Data);
        else {
          alert("Failed to get data. "+response?.Message);
          this.navigateToDashboard();
        }
      });

  }

  private addNote(): void {
    this._notesService
      .addNote(this._appSettings.Cnct_PKey, this.formGroup.getRawValue())
      .subscribe(response => {
        if(response?.Status == SuccessFailedEnum.SUCCESS)
          this.navigateToDashboard();
        else
          alert("Failed to add note. "+response?.Message);
      });
  }

  private editNote(): void {
    this._notesService
    .updateNote(this.formGroup.get("Notes_PKey").value, this.formGroup.getRawValue())
    .subscribe(response => {
      if(response?.Status == SuccessFailedEnum.SUCCESS)
        this.navigateToDashboard();
      else
        alert("Failed to update note. "+response?.Message);
    });
  }

  //#endregion

}
