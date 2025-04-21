import { Injectable } from "@angular/core";
import { AppSettingsDto, getAppSettingsInstance } from "../models/AppSettingsDto.model";
import { AppSessionEnum } from "../enums/app-session.enum";

@Injectable({
    providedIn: 'root'
  })
  export class AppSettingsService {    
  
    constructor() { }
  
    public getAppSettings(): AppSettingsDto {
        let appSettings: AppSettingsDto = {
            ...getAppSettingsInstance()
        };

        appSettings = JSON.parse(sessionStorage.getItem(AppSessionEnum.AppSettings));

        return appSettings;
    }

    public setAppSettings(appSettings: AppSettingsDto): void {
        sessionStorage.setItem(AppSessionEnum.AppSettings,JSON.stringify(appSettings));
    }

    public clearSessionStorage(): void {
        sessionStorage.clear();
    }

}