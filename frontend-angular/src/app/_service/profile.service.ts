import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../_model/user';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  _user: User = new User();

  constructor(private http: HttpClient, private authService: AuthService) {
    if (this.authService.isLoggedIn) {
      this.refresh();
    }
  }

  details(): Observable<User> {
    return this.http.get<User>('http://localhost:8080/api/profile');
  }

  refresh() {
    this.details().subscribe(user => {
      this._user = user;
    });
  }

  get user(): User {
    return this._user;
  }

  changePassword(user: User): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/profile/change_password',
      user
    );
  }

  changeImage(data: FormData): Observable<any> {
    return this.http.post(
      'http://localhost:8080/api/profile/change_image',
      data
    );
  }
}
