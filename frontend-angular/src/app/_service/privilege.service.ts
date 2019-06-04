import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Privilege } from '../_model/privilege';
import { ProfileService } from './profile.service';

@Injectable({
  providedIn: 'root'
})
export class PrivilegeService {
  privileges: Privilege[];

  constructor(private http: HttpClient, private authService: AuthService, private profileService: ProfileService) {
    if (this.authService.isLoggedIn) {
      this.init();
    }
  }

  init() {
    this.load();
  }

  load() {
    this.http
      .get<Privilege[]>('http://localhost:8080/api/privileges')
      .subscribe(data => {
        this.privileges = data;
      });
  }

  hasPrivilege(name: string): boolean {
    if (!this.profileService.user.authorities || !this.privileges) { return false; }
    let result = false;

    this.profileService.user.authorities.forEach(authority => {
      if (!result && (authority.authority === 'ALL_PRIVILEGES' || (authority.authority === name && this.isActive(name)))) {
        result = true;
        return;
      }
    });

    return result;
  }

  isActive(name: string): boolean {
    let result = false;
    this.privileges.forEach(privilege => {
      if (privilege.name === name) {
        result = true;
        return;
      }
    });
    return result;
  }

  getShow(name: string): string {
    let result = null;
    if (this.privileges) {
      this.privileges.forEach(privilege => {
        if (!result && privilege.name === name) {
          result = privilege.show;
          return;
        }
      });
    }
    return result;
  }

  findAll(): Observable<Privilege[]> {
    return this.http.get<Privilege[]>('http://localhost:8080/api/privileges');
  }

  edit(element: Privilege): Observable<any> {
    return this.http.put(`http://localhost:8080/api/privileges/${element.id}`, element);
  }

  enable(element: Privilege): Observable<any> {
    return this.http.put(`http://localhost:8080/api/privileges/${element.id}/enable`, null);
  }

  disable(element: Privilege): Observable<any> {
    return this.http.put(`http://localhost:8080/api/privileges/${element.id}/disable`, null);
  }
}
