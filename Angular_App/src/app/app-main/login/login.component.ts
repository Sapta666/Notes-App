import { AfterViewInit, Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NavigationPageEnum } from 'src/app/common/navigation-page.enum';
import { getAddEditNoteInstance } from 'src/app/models/AddEditNoteDto.model';
import { getLoginInstance } from 'src/app/models/LoginDto.model';
import { getUserSignUpInstance } from 'src/app/models/UserSignUpDto.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements AfterViewInit {

  //#region Variables

  private _note_PKey: string = "";

  protected formGroup: FormGroup;

  protected listHeight: number = 100;
  protected breakpointActivated: boolean = false;

  //#endregion

  //#region Page Load

  constructor(
    private _fb: FormBuilder,
    private _notesService: NotesService,
    private _router: Router,
  ) {
    this.initializeFormGroup();
  }

  ngAfterViewInit(): void {

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
    this.formGroup = this._fb.group({ ...getUserSignUpInstance() });

    this.formGroup.controls["Username"].setValidators([Validators.required]);
    this.formGroup.controls["Password"].setValidators([Validators.required]);
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
