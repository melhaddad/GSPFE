import { UserService } from './_service/user.service';
import { SecurityService } from './_service/security.service';
import { RoleService } from './_service/role.service';
import { PrivilegeService } from './_service/privilege.service';
import { NotificationService } from './_service/notification.service';
import { NamingService } from './_service/naming.service';
import { ItemService } from './_service/item.service';
import { HistoryService } from './_service/history.service';
import { CategoryService } from './_service/category.service';
import { AuthService } from './_service/auth.service';
import { AssignmentService } from './_service/assignment.service';
import { AuthInterceptor } from './_service/auth-interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgZorroAntdModule, NZ_I18N, fr_FR } from 'ng-zorro-antd';
import { registerLocaleData } from '@angular/common';
import fr from '@angular/common/locales/fr';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { MenuComponent } from './menu/menu.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { AppRoutingModule } from './/app-routing.module';
import { CategoryComponent } from './category/category.component';
import { ItemComponent } from './item/item.component';
import { UserComponent } from './user/user.component';
import { RoleComponent } from './role/role.component';
import { PrivilegeComponent } from './privilege/privilege.component';
import { NotificationComponent } from './notification/notification.component';
import { AssignmentComponent } from './assignment/assignment.component';
import { HistoryComponent } from './history/history.component';
import { ProfileComponent } from './profile/profile.component';
import { HistoryCategoryDetailComponent } from './history-category-detail/history-category-detail.component';
import { HistoryItemDetailComponent } from './history-item-detail/history-item-detail.component';
import { HistoryUserDetailComponent } from './history-user-detail/history-user-detail.component';
import { HistoryAssignmentDetailComponent } from './history-assignment-detail/history-assignment-detail.component';
import { HistoryRoleDetailComponent } from './history-role-detail/history-role-detail.component';
import { HistoryPrivilegeDetailComponent } from './history-privilege-detail/history-privilege-detail.component';
import { ChangePasswordFormComponent } from './change-password-form/change-password-form.component';
import { ChangeImageFormComponent } from './change-image-form/change-image-form.component';
import { ItemSupplyFormComponent } from './item-supply-form/item-supply-form.component';
import { UserRoleFormComponent } from './user-role-form/user-role-form.component';
import { ProfileService } from './_service/profile.service';

registerLocaleData(fr);

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    MenuComponent,
    LoginFormComponent,
    CategoryComponent,
    ItemComponent,
    UserComponent,
    RoleComponent,
    PrivilegeComponent,
    NotificationComponent,
    AssignmentComponent,
    HistoryComponent,
    ProfileComponent,
    HistoryCategoryDetailComponent,
    HistoryItemDetailComponent,
    HistoryUserDetailComponent,
    HistoryAssignmentDetailComponent,
    HistoryRoleDetailComponent,
    HistoryPrivilegeDetailComponent,
    ChangePasswordFormComponent,
    ChangeImageFormComponent,
    ItemSupplyFormComponent,
    UserRoleFormComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgZorroAntdModule,
    AppRoutingModule
  ],
  providers: [
    AssignmentService,
    AuthService,
    CategoryService,
    HistoryService,
    ItemService,
    NamingService,
    NotificationService,
    PrivilegeService,
    ProfileService,
    RoleService,
    SecurityService,
    UserService,
    { provide: NZ_I18N, useValue: fr_FR },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
