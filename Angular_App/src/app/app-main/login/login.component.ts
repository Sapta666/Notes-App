import { AfterViewInit, Component, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NavigationPageEnum } from 'src/app/common/enums/navigation-page.enum';
import { AppSettingsService } from 'src/app/common/services/app-settings.service';
import { CryptoUtils } from 'src/app/common/utils/crypto-utils';
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
    private _router: Router,
    private _appSettingsService: AppSettingsService,
    private _authService: AuthService,    
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

  private validateData(): boolean {
    let errorMessage: string = "";
    try {
      this.formGroup.markAllAsTouched();  

      // extra validation for user signup
      if(this.actionType == 'SIGNUP') {
        if (this.formGroup.controls["FirstName"].value.trim() == "")
          errorMessage += "\n First name is required."
        if (this.formGroup.controls["LastName"].value.trim() == "")
          errorMessage += "\n Last name is required."
      }
      
      // mandatory validation needed for both login and sign up
      if (this.formGroup.controls["Username"].value.trim() == "")
        errorMessage += "\n Username is required."
      if (this.formGroup.controls["Password"].value.trim() == "")
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

  private navigateToDashboard(): void {
    this._router.navigate([`${NavigationPageEnum.Dashboard}`]);
  }

  //#endregion    

  //#region Component Functions  

  protected onSubmitClick(): void {    
    if (this.validateData()) {      
      if (this.actionType == "LOGIN") {
        let login: LoginDto = {
          Username: CryptoUtils.encodeText(this.formGroup.controls["Username"].value),
          Password: CryptoUtils.encodeText(this.formGroup.controls["Password"].value),
        }
        this.loginUser(login);
      } else {
        let userSignUp: UserSignUpDto = {
          Username: CryptoUtils.encodeText(this.formGroup.controls["Username"].value),
          Password: CryptoUtils.encodeText(this.formGroup.controls["Password"].value),
          FirstName: CryptoUtils.encodeText(this.formGroup.controls["FirstName"].value),
          LastName: CryptoUtils.encodeText(this.formGroup.controls["LastName"].value),
        };
        this.signUp(userSignUp);
      }
    }
  }

  protected onChangeActionTypeClick(): void {
    if(this.actionType == "LOGIN")
      this.actionType = "SIGNUP";
    else  
      this.actionType = "LOGIN";
  }

  //#endregion

  //#region Service Functions

  private loginUser(login: LoginDto): void {
    this._authService
      .login(login)
      .subscribe(response => {
        if(response?.Status?.toUpperCase() == "SUCCESS") {
          this._appSettingsService.setAppSettings(response.Data);
          this.navigateToDashboard();
        } else  
          alert("Login failed! Invalid username or password.");
      });
  }

  private signUp(userSignUp: UserSignUpDto): void {
    this._authService
      .signUp(userSignUp)
      .subscribe(response => {
        if(response?.Status?.toUpperCase() == "SUCCESS") {
          this._appSettingsService.setAppSettings(response.Data);
          this.navigateToDashboard();
        } else  
          alert("User signup failed! "+response?.Message);
      });
  }  

  //#endregion

}
