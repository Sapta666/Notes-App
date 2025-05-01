import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { NavigationPageEnum } from 'src/app/common/enums/navigation-page.enum';
import { AppSettingsDto } from 'src/app/common/models/AppSettingsDto.model';
import { AppSettingsService } from 'src/app/common/services/app-settings.service';

@Component({
  selector: 'app-page-header-control',
  templateUrl: './page-header-control.component.html',
})
export class PageHeaderControlComponent {

  protected appSettings: AppSettingsDto;

  constructor(
    private _router: Router,
    private _appSettingsService: AppSettingsService
  ) {
    this.appSettings = this._appSettingsService.getAppSettings();
  }

  protected onHomeClick(): void {
    this._router.navigate([`${NavigationPageEnum.Dashboard}`]);
  }

  protected onLogoutClick(): void {
    const userConfirmed = confirm("Are you sure you want to logout?");

    if (userConfirmed) {
      this._appSettingsService.clearSessionStorage();
      this._router.navigate([`${NavigationPageEnum.Login}`]);
    }

  }

}
