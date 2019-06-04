import { Category } from './../_model/category';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  constructor(private http: HttpClient) {}

  findAll(): Observable<Category[]> {
    return this.http.get<Category[]>('http://localhost:8080/api/categories');
  }

  save(element: Category): Observable<any> {
    return this.http.post('http://localhost:8080/api/categories', element);
  }

  delete(id): Observable<any>  {
    return this.http.delete(`http://localhost:8080/api/categories/${id}`);
  }

  edit(element: Category): Observable<any>  {
    return this.http.put(
      `http://localhost:8080/api/categories/${element.id}`,
      element
    );
  }
}
