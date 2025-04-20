import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable()
export class BaseUrlInterceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let url: string = request.clone().url;
    url = url.replace("{{baseUrl}}", environment.endPoint);

    let modifiedRequest = request.clone({
      url: url,
    })

    return next.handle(modifiedRequest);;
  }

}