import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from "@angular/material/button";
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatBadgeModule} from '@angular/material/badge';
import {MatCardModule} from '@angular/material/card';

import { MATERIAL_PRAC_SHARED } from './shared';
import { APP_MAIN } from './app-main';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BaseUrlInterceptor } from './common/interceptors/base-url.interceptor';
import { ErrorHandlingInterceptor } from './common/interceptors/error-handling.interceptor';

@NgModule({
  declarations: [
    AppComponent,

    ...APP_MAIN,
    ...MATERIAL_PRAC_SHARED,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule, 
    ReactiveFormsModule,
    HttpClientModule,

    AppRoutingModule,   

    MatButtonModule,
    MatButtonToggleModule,
    MatBadgeModule,
    MatCardModule,
  ],
  providers: [
    {
        provide: HTTP_INTERCEPTORS, useClass: BaseUrlInterceptor, multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS, useClass: ErrorHandlingInterceptor, multi: true,
    }    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }