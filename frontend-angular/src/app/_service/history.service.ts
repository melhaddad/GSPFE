import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HistoryService {

  constructor(private http: HttpClient) { }

  findAll(url: string): Observable<any> {
    return this.http.get(`http://localhost:8080/api/${url}/history`);
  }
}
