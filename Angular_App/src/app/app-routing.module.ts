import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavigationPageEnum } from './common/navigation-page.enum';
import { APP_MAIN_ROUTING } from './app-main/routing';

const routes: Routes = [
  { path: "", pathMatch: "full", redirectTo: NavigationPageEnum.Login },  
  

  ...APP_MAIN_ROUTING,
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
