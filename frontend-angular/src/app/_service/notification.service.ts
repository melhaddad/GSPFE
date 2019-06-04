import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Notification } from './../_model/notification';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  notifications: Notification[] = [];

  constructor(private http: HttpClient, private auth: AuthService) {
    this.load();
    setInterval(() => {
      if (this.auth.isLoggedIn) {
        this.load();
      }
    }, 10000);
  }

  load() {
    this.http.get<Notification[]>('http://localhost:8080/api/notifications').subscribe((data) => {
      this.notifications = data;
    });
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/api/notifications/${id}`);
  }

  get hasNotification(): boolean {
    return this.notifications.length > 0;
  }

  get notificationCount(): number {
    return this.notifications.length;
  }
}
