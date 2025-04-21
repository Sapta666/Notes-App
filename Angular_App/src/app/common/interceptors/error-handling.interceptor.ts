import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpEventType, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, of, tap, throwError } from "rxjs";
import { getBasicResponseInstance } from "../models/BasicResponseDto.model";


@Injectable({
    providedIn: "root"
})
export class ErrorHandlingInterceptor implements HttpInterceptor {

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
                else    
                    alert("Something went wrong!");

                return of(null);
            })
        );
    }

}