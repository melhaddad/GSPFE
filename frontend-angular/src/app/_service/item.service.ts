import { Supply } from './../_model/supply';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Item } from './../_model/item';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  constructor(private http: HttpClient) { }

  findAll(): Observable<Item[]> {
    return this.http.get<Item[]>('http://localhost:8080/api/items');
  }

  save(element: Item): Observable<any> {
    return this.http.post('http://localhost:8080/api/items', element);
  }

  delete(id): Observable<any> {
    return this.http.delete(`http://localhost:8080/api/items/${id}`);
  }

  edit(element: Item): Observable<any> {
    return this.http.put(
      `http://localhost:8080/api/items/${element.id}`,
      element
    );
  }

  supply(element: Item, supply: Supply): Observable<any> {
    return this.http.put(
      `http://localhost:8080/api/items/${element.id}/supply`,
      supply
    );
  }
}
