import { Routes } from "@angular/router";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { NavigationPageEnum } from "../common/enums/navigation-page.enum";
import { AddEditNoteComponent } from "./add-edit-note/add-edit-note.component";
import { LoginComponent } from "./login/login.component";
import { authGuard } from "../common/guards/auth.guard";

export const APP_MAIN_ROUTING: Routes = [
    { path: NavigationPageEnum.Login, component: LoginComponent },    

    { path: NavigationPageEnum.Dashboard, component: DashboardComponent, canActivate: [authGuard] },
    { path: `${NavigationPageEnum.AddEditNote}/:AddEdit`, component: AddEditNoteComponent, canActivate: [authGuard] },    
];