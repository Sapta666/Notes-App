import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpEventType, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, of, tap, throwError } from "rxjs";
import { getBasicResponseInstance } from "../models/BasicResponseDto.model";
import { Route, Router } from "@angular/router";
import { NavigationPageEnum } from "../enums/navigation-page.enum";
import { AppSettingsService } from "../services/app-settings.service";


@Injectable({
    providedIn: "root"
})
export class ErrorHandlingInterceptor implements HttpInterceptor {

    constructor(
        private readonly _route: Router,
        private readonly _appSettingsService: AppSettingsService,
    ) {

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        return next.handle(req).pipe(
            catchError((err: HttpErrorResponse) => {

                if(err.status == 500)
                    alert("Internal Server Error!");
                else if(err.status == 404)
                    alert("Invalid Url!")
                else if(err.status == 405)
                    alert("Method Not Allowed!");
                else if(err.status == 400)
                    alert("Client Side Error!");
                else if(err?.error?.IsJWTExpired) {
                    alert(err?.error?.Message);
                    this._route.navigate([`${NavigationPageEnum.Login}`]);
                    this._appSettingsService.clearSessionStorage();
                } else    
                    alert("Something went wrong! "+err?.error?.Message);

                return of(null);
            })
        );
    }

}