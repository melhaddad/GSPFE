import { Assignment } from './../_model/assignment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AssignmentService {

  constructor(private http: HttpClient) { }

  findAll(url: string): Observable<Assignment[]> {
    return this.http.get<Assignment[]>(`http://localhost:8080/api/users_items${url}`);
  }

  request(element: Assignment): Observable<any> {
    return this.http.post(`http://localhost:8080/api/users_items`, element);
  }

  accept(element: Assignment): Observable<any> {
    return this.http.put(`http://localhost:8080/api/users_items/${element.id}/accept/${element.item.id}/${element.quantity}`, null);
  }

  refuse(element: Assignment): Observable<any> {
    return this.http.put(`http://localhost:8080/api/users_items/${element.id}/refuse`, null);
  }

  deliver(element: Assignment): Observable<any> {
    return this.http.put(`http://localhost:8080/api/users_items/${element.id}/deliver`, null);
  }

  return(element: Assignment): Observable<any> {
    return this.http.put(`http://localhost:8080/api/users_items/${element.id}/returning`, null);
  }

  take(element: Assignment): Observable<any> {
    return this.http.put(`http://localhost:8080/api/users_items/${element.id}/return`, null);
  }
}
