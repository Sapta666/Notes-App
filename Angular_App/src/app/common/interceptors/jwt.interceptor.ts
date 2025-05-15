import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';
import { AppSettingsService } from '../services/app-settings.service';

@Injectable()
export class JWTInterceptor implements HttpInterceptor {

    constructor(
        private _appSettingsService: AppSettingsService,
    ) {

    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token: string = this._appSettingsService.getAppSettings()?.Token;
        let finalToken: string = `Bearer ${token?.trim()}`;
        let modifiedRequest = request.clone();

        if (token?.length > 0)
            modifiedRequest = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${token}`,
                    
                } 
            });
        // console.log(modifiedRequest.headers.get("Authorization"));
        // console.log(finalToken);
 
        return next.handle(modifiedRequest);;
    }

}