import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { User } from '../_model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<User[]> {
    return this.http.get<User[]>('http://localhost:8080/api/users');
  }

  save(element: User): Observable<any> {
    return this.http.post('http://localhost:8080/api/users', element);
  }

  edit(element: User): Observable<any> {
    element.password = 'ignored password';
    return this.http.put(`http://localhost:8080/api/users/${element.id}`, element);
  }

  delete(id): Observable<any> {
    return this.http.delete(`http://localhost:8080/api/users/${id}`);
  }

  roles(element: User): Observable<any> {
    return this.http.put(`http://localhost:8080/api/users/${element.id}/roles`, element.roles);
  }
}
