import { AuthGuardService } from './_service/auth-guard.service';
import { ProfileComponent } from './profile/profile.component';
import { HistoryComponent } from './history/history.component';
import { PrivilegeComponent } from './privilege/privilege.component';
import { RoleComponent } from './role/role.component';
import { AssignmentComponent } from './assignment/assignment.component';
import { UserComponent } from './user/user.component';
import { ItemComponent } from './item/item.component';
import { CategoryComponent } from './category/category.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'login', component: LoginFormComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService] },
  { path: 'categories', component: CategoryComponent, canActivate: [AuthGuardService] },
  { path: 'items', component: ItemComponent, canActivate: [AuthGuardService] },
  { path: 'users', component: UserComponent, canActivate: [AuthGuardService] },
  { path: 'assignments', component: AssignmentComponent, canActivate: [AuthGuardService] },
  { path: 'roles', component: RoleComponent, canActivate: [AuthGuardService] },
  { path: 'privileges', component: PrivilegeComponent, canActivate: [AuthGuardService] },
  { path: 'history', component: HistoryComponent, canActivate: [AuthGuardService] },
  { path: '', redirectTo: '/assignments', pathMatch: 'full' }
];

@NgModule({
  exports: [RouterModule],
  imports: [RouterModule.forRoot(routes)]
})
export class AppRoutingModule { }
