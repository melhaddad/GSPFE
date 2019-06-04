import { Notification } from './../_model/notification';
import { NotificationService } from './../_service/notification.service';
import { Component, OnInit, Input, Output } from '@angular/core';
import { User } from '../_model/user';
import { ProfileService } from '../_service/profile.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {
  closing = [];

  constructor(private profileService: ProfileService, public notificationService: NotificationService) { }

  ngOnInit() { }

  get user(): User {
    return this.profileService.user;
  }

  get notifications(): Notification[] {
    return this.notificationService.notifications;
  }

  date(date: string): string {
    return new Date(date).toLocaleString();
  }

  delete(element: Notification) {
    this.closing[element.id] = true;
    this.notificationService.delete(element.id).subscribe(() => {
      this.closing[element.id] = false;
      this.notificationService.load();
    });
  }

}
