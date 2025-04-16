import { Routes } from "@angular/router";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { NavigationPageEnum } from "../common/navigation-page.enum";
import { AddEditNoteComponent } from "./add-edit-note/add-edit-note.component";
import { LoginComponent } from "./login/login.component";

export const APP_MAIN_ROUTING: Routes = [
    { path: NavigationPageEnum.Login, component: LoginComponent },    
    { path: NavigationPageEnum.Dashboard, component: DashboardComponent },
    { path: `${NavigationPageEnum.AddEditNote}/:AddEdit`, component: AddEditNoteComponent },    
];