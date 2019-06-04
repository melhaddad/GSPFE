import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Role } from '../_model/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Role[]> {
    return this.http.get<Role[]>('http://localhost:8080/api/roles');
  }

  save(element: Role): Observable<any> {
    return this.http.post('http://localhost:8080/api/roles', element);
  }

  edit(element: Role): Observable<any> {
    return this.http.put(`http://localhost:8080/api/roles/${element.id}`, element);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/api/roles/${id}`);
  }
}
