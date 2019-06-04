import { Injectable } from '@angular/core';
import { PrivilegeService } from './privilege.service';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private privilegeService: PrivilegeService) { }

  get canShowProfile(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_PROFILE');
  }

  get canUpdatePassword(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_PASSWORD');
  }

  get canUpdateProfileImage(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_PROFILE_IMAGE');
  }

  get canUpdatePasswords(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_PASSWORDS');
  }

  get canShowCategories(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_CATEGORIES');
  }

  get canAddCategory(): boolean {
    return this.privilegeService.hasPrivilege('ADD_CATEGORY');
  }

  get canUpdateCategory(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_CATEGORY');
  }

  get canDeleteCategory(): boolean {
    return this.privilegeService.hasPrivilege('DELETE_CATEGORY');
  }

  get canShowItems(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_ITEMS');
  }

  get canAddItem(): boolean {
    return this.privilegeService.hasPrivilege('ADD_ITEM');
  }

  get canUpdateItem(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_ITEM');
  }

  get canSupplyItem(): boolean {
    return this.privilegeService.hasPrivilege('SUPPLY_ITEM');
  }

  get canDeleteItem(): boolean {
    return this.privilegeService.hasPrivilege('DELETE_ITEM');
  }

  get canShowUsers(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_USERS');
  }

  get canAddUser(): boolean {
    return this.privilegeService.hasPrivilege('ADD_USER');
  }

  get canUpdateUser(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_USER');
  }

  get canDeleteUser(): boolean {
    return this.privilegeService.hasPrivilege('DELETE_USER');
  }

  get canShowRoles(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_ROLES');
  }

  get canAddRole(): boolean {
    return this.privilegeService.hasPrivilege('ADD_ROLE');
  }

  get canUpdateRole(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_ROLE');
  }

  get canDeleteRole(): boolean {
    return this.privilegeService.hasPrivilege('DELETE_ROLE');
  }

  get canUpdateUserRole(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_USER_ROLE');
  }

  get canShowPrivileges(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_PRIVILEGES');
  }

  get canEnablePrivilege(): boolean {
    return this.privilegeService.hasPrivilege('ENABLE_PRIVILEGE');
  }

  get canUpdatePrivilege(): boolean {
    return this.privilegeService.hasPrivilege('UPDATE_PRIVILEGE');
  }

  get canDisablePrivilege(): boolean {
    return this.privilegeService.hasPrivilege('DISABLE_PRIVILEGE');
  }

  get canShowHistory(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_HISTORY');
  }

  get canShowUserItem(): boolean {
    return this.privilegeService.hasPrivilege('SHOW_USER_ITEMS');
  }

  get canRequestItem(): boolean {
    return this.privilegeService.hasPrivilege('REQUEST_ITEM');
  }

  get canAcceptItem(): boolean {
    return this.privilegeService.hasPrivilege('ACCEPT_ITEM');
  }

  get canRefuseItem(): boolean {
    return this.privilegeService.hasPrivilege('REFUSE_ITEM');
  }

  get canDeliverItem(): boolean {
    return this.privilegeService.hasPrivilege('DELIVER_ITEM');
  }

  get canReturnItem(): boolean {
    return this.privilegeService.hasPrivilege('RETURN_ITEM');
  }

  get canTakeItem(): boolean {
    return this.privilegeService.hasPrivilege('TAKE_ITEM');
  }
}
