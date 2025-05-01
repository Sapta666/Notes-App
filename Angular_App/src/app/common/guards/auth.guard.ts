import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { AppSettingsService } from "../services/app-settings.service";
import { NavigationPageEnum } from "../enums/navigation-page.enum";


export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree => {
    const appSettingsService = inject(AppSettingsService);
    const router = inject(Router);

    const urlTree: UrlTree = router.createUrlTree([`${NavigationPageEnum.Login}`]);

    if(!appSettingsService.getAppSettings())        
        return urlTree;

    return true;
}