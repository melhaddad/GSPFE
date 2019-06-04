import { PrivilegeService } from './privilege.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NamingService {

  constructor(private privilegeService: PrivilegeService) { }


  get getShowProfile(): string {
    return this.privilegeService.getShow('SHOW_PROFILE');
  }

  get getUpdatePassword(): string {
    return this.privilegeService.getShow('UPDATE_PASSWORD');
  }

  get getUpdateProfileImage(): string {
    return this.privilegeService.getShow('UPDATE_PROFILE_IMAGE');
  }

  get getUpdatePasswords(): string {
    return this.privilegeService.getShow('UPDATE_PASSWORDS');
  }

  get getShowCategories(): string {
    return this.privilegeService.getShow('SHOW_CATEGORIES');
  }

  get getAddCategory(): string {
    return this.privilegeService.getShow('ADD_CATEGORY');
  }

  get getUpdateCategory(): string {
    return this.privilegeService.getShow('UPDATE_CATEGORY');
  }

  get getDeleteCategory(): string {
    return this.privilegeService.getShow('DELETE_CATEGORY');
  }

  get getShowItems(): string {
    return this.privilegeService.getShow('SHOW_ITEMS');
  }

  get getAddItem(): string {
    return this.privilegeService.getShow('ADD_ITEM');
  }

  get getUpdateItem(): string {
    return this.privilegeService.getShow('UPDATE_ITEM');
  }

  get getSupplyItem(): string {
    return this.privilegeService.getShow('SUPPLY_ITEM');
  }

  get getDeleteItem(): string {
    return this.privilegeService.getShow('DELETE_ITEM');
  }

  get getShowUsers(): string {
    return this.privilegeService.getShow('SHOW_USERS');
  }

  get getAddUser(): string {
    return this.privilegeService.getShow('ADD_USER');
  }

  get getUpdateUser(): string {
    return this.privilegeService.getShow('UPDATE_USER');
  }

  get getDeleteUser(): string {
    return this.privilegeService.getShow('DELETE_USER');
  }

  get getShowRoles(): string {
    return this.privilegeService.getShow('SHOW_ROLES');
  }

  get getAddRole(): string {
    return this.privilegeService.getShow('ADD_ROLE');
  }

  get getUpdateRole(): string {
    return this.privilegeService.getShow('UPDATE_ROLE');
  }

  get getDeleteRole(): string {
    return this.privilegeService.getShow('DELETE_ROLE');
  }

  get getUpdateUserRole(): string {
    return this.privilegeService.getShow('UPDATE_USER_ROLE');
  }

  get getShowPrivileges(): string {
    return this.privilegeService.getShow('SHOW_PRIVILEGES');
  }

  get getEnablePrivilege(): string {
    return this.privilegeService.getShow('ENABLE_PRIVILEGE');
  }

  get getUpdatePrivilege(): string {
    return this.privilegeService.getShow('UPDATE_PRIVILEGE');
  }

  get getDisablePrivilege(): string {
    return this.privilegeService.getShow('DISABLE_PRIVILEGE');
  }

  get getShowHistory(): string {
    return this.privilegeService.getShow('SHOW_HISTORY');
  }

  get getShowUserItem(): string {
    return 'Affectations';
  }

  get getRequestItem(): string {
    return this.privilegeService.getShow('REQUEST_ITEM');
  }

  get getAcceptItem(): string {
    return this.privilegeService.getShow('ACCEPT_ITEM');
  }

  get getRefuseItem(): string {
    return this.privilegeService.getShow('REFUSE_ITEM');
  }

  get getDeliverItem(): string {
    return this.privilegeService.getShow('DELIVER_ITEM');
  }

  get getReturnItem(): string {
    return this.privilegeService.getShow('RETURN_ITEM');
  }

  get getTakeItem(): string {
    return this.privilegeService.getShow('TAKE_ITEM');
  }
}
