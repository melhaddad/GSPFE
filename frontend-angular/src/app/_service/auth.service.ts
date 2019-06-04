import { JwtService } from './jwt.service';
import { UserCredentials } from '../_model/user-credentials';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { User } from '../_model/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private jwtService: JwtService,
  ) { }

  login(credentials: UserCredentials): Promise<any> {
    return new Promise((resolve, reject) => {
      this.http
        .post('http://localhost:8080/login', credentials, {
          observe: 'response'
        })
        .subscribe(response => {
          this.startSession(response.headers.get('Authorization'));
          resolve();
        }, () => reject());
    });
  }

  logout(): void {
    this.destroySession();
  }

  get isLoggedIn(): boolean {
    let expire_at: any = localStorage.getItem('expires_at');

    if (!expire_at) {
      return false;
    }

    expire_at = parseFloat(expire_at) * 1000;

    return moment().isBefore(moment(expire_at));
  }

  get user(): User {
    return JSON.parse(localStorage.getItem('user'));
  }

  private startSession(token: string): void {
    const jwt = this.jwtService.getJwtFromHeader(token);
    const expire_at = this.jwtService.decodeAndGet(jwt, 'exp');

    const user = new User();

    user.email = this.jwtService.decodeAndGet(jwt, 'sub');
    user.firstName = this.jwtService.decodeAndGet(jwt, 'firstName');
    user.lastName = this.jwtService.decodeAndGet(jwt, 'lastName');
    user.image = this.jwtService.decodeAndGet(jwt, 'image');
    user.privileges = this.jwtService.decodeAndGet(jwt, 'privileges');

    localStorage.setItem('user', JSON.stringify(user));
    localStorage.setItem('id_token', jwt);
    localStorage.setItem('expires_at', expire_at.toString());
  }

  private destroySession(): void {
    localStorage.clear();
  }
}
