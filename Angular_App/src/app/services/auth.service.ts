import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginDto } from "../models/function-model/auth/LoginDto.model";
import { AppSettingsDto } from "../common/models/AppSettingsDto.model";
import { ResponseDataDto } from "../common/models/ResponseDataDto.model";
import { UserSignUpDto } from "../models/function-model/auth/UserSignUpDto.model";

@Injectable({
    providedIn: 'root'
  })
  export class AuthService {    
  
    constructor(private _httpClient: HttpClient) { }
  
    public login(login: LoginDto): Observable<ResponseDataDto<AppSettingsDto>> {
      return this._httpClient
        .post<ResponseDataDto<AppSettingsDto>>
        (`{{baseUrl}}/auth/login`,login);          
    }

    public signUp(userSignUp: UserSignUpDto): Observable<ResponseDataDto<AppSettingsDto>> {
        return this._httpClient
          .post<ResponseDataDto<AppSettingsDto>>
          (`{{baseUrl}}/auth/signup`,userSignUp);          
      }

}