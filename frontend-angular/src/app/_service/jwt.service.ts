import { Injectable } from '@angular/core';
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  bearer = 'Bearer ';
  constructor() { }
é
  getJwtFromHeader(header: string): string {
    const jwt = header.substr(this.bearer.length);
    return jwt;
  }

  decode(jwt: string): Object {
    return jwt_decode(jwt);
  }

  get(decode: Object, attr: string): any {
    return decode[attr];
  }

  decodeAndGet(jwt: string, attr: string) {
    return this.get(this.decode(jwt), attr);
  }
}
