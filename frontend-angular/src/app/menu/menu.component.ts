import { NamingService } from './../_service/naming.service';
import { AuthService } from '../_service/auth.service';
import { Component, OnInit, Input } from '@angular/core';
import { PrivilegeService } from '../_service/privilege.service';
import { SecurityService } from '../_service/security.service';
import { Router } from '@angular/router';
import { MenuItem } from '../_model/menu-item';
import { jsonpCallbackContext } from '@angular/common/http/src/module';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  // menus = [
  //   { text: this.naming.getShowProfile, show: this.security.canShowProfile, link: '/profile', icon: 'profile' },
  //   { text: this.naming.getShowCategories, show: this.security.canShowCategories, link: '/categories', icon: 'api' },
  //   { text: this.naming.getShowItems, show: this.security.canShowItems, link: '/items', icon: 'laptop' },
  //   { text: this.naming.getShowUsers, show: this.security.canShowUsers, link: '/users', icon: 'contacts' },
  //   { text: this.naming.getShowUserItem, show: this.authService.isLoggedIn, link: '/assignments', icon: 'swap' },
  //   { text: this.naming.getShowRoles, show: this.security.canShowRoles, link: '/roles', icon: 'user' },
  //   { text: this.naming.getShowPrivileges, show: this.security.canShowPrivileges, link: '/privileges', icon: 'lock' },
  //   { text: this.naming.getShowHistory, show: this.security.canShowHistory, link: '/history', icon: 'hourglass' },
  //   { text: 'Déconnexion', show: this.authService.isLoggedIn, link: '/login', icon: 'logout' }
  // ];

  @Input()
  isCollapsed: Boolean;

  constructor(
    public authService: AuthService,
    private router: Router,
    public security: SecurityService,
    public naming: NamingService
  ) { }

  ngOnInit() { }

  get selected(): string {
    return this.router.url;
  }

  get menus() {
    const result: MenuItem[] = [];

    if (this.security.canShowProfile) {
      result.push(new MenuItem(this.naming.getShowProfile, '/profile', 'profile'));
    }
    if (this.security.canShowCategories) {
      result.push(new MenuItem(this.naming.getShowCategories, '/categories', 'api'));
    }
    if (this.security.canShowItems) {
      result.push(new MenuItem(this.naming.getShowItems, '/items', 'laptop'));
    }
    if (this.security.canShowUsers) {
      result.push(new MenuItem(this.naming.getShowUsers, '/users', 'contacts'));
    }
    if (this.authService.isLoggedIn) {
      result.push(new MenuItem(this.naming.getShowUserItem, '/assignments', 'swap'));
    }
    if (this.security.canShowRoles) {
      result.push(new MenuItem(this.naming.getShowRoles, '/roles', 'user'));
    }
    if (this.security.canShowPrivileges) {
      result.push(new MenuItem(this.naming.getShowPrivileges, '/privileges', 'lock'));
    }
    if (this.security.canShowHistory) {
      result.push(new MenuItem(this.naming.getShowHistory, '/history', 'hourglass'));
    }
    if (this.authService.isLoggedIn) {
      result.push(
        new MenuItem(
          'Déconnexion',
          '/login',
          'logout',
          () => { this.authService.logout(); }
        ));
    }

    return result;
  }

  handle(menu: MenuItem) {
    console.log(JSON.stringify(menu));
    menu.action();
  }
}
