import { AfterViewInit, Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NavigationPageEnum } from 'src/app/common/navigation-page.enum';
import { LoginDto } from 'src/app/models/function-model/auth/LoginDto.model';
import { getUserSignUpInstance, UserSignUpDto } from 'src/app/models/function-model/auth/UserSignUpDto.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements AfterViewInit {

  //#region Variables

  private _note_PKey: string = "";

  protected formGroup: FormGroup;
  protected actionType: "LOGIN" | "SIGNUP" = "LOGIN";

  protected listHeight: number = 100;
  protected breakpointActivated: boolean = false;

  //#endregion

  //#region Page Load

  constructor(
    private _fb: FormBuilder,
    private _authService: AuthService,
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
      if (this.actionType == "LOGIN") {
        let login: LoginDto = {
          Username: this.formGroup.controls["Username"].value,
          Password: this.formGroup.controls["Password"].value,
        }
        this.loginUser(login);
      } else {
        let userSignUp: UserSignUpDto = {
          Username: this.formGroup.controls["Username"].value,
          Password: this.formGroup.controls["Password"].value,
          FirstName: this.formGroup.controls["FirstName"].value,
          LastName: this.formGroup.controls["LastName"].value,
        };
        this.signUp(userSignUp);
      }

        this.navigateToDashboard();
    }
  }

  //#endregion

  //#region Service Functions

  private loginUser(login: LoginDto): void {
    this._authService
      .login(login)
      .subscribe(response => {
        if(response?.Status?.toUpperCase() == "SUCCESS") {

        } else  
          alert("Login failed! Invalid username or password.");
      });
  }

  private signUp(userSignUp: UserSignUpDto): void {
    this._authService
      .signUp(userSignUp)
      .subscribe(response => {
        if(response?.Status?.toUpperCase() == "SUCCESS") {

        } else  
          alert("User signup failed! "+response?.Message);
      });
  }

  //#endregion

}
