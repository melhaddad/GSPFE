import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_service/auth.service';
import { NamingService } from '../_service/naming.service';
import { SecurityService } from '../_service/security.service';
import { User } from '../_model/user';
import { ProfileService } from '../_service/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(
    private service: ProfileService,
    public security: SecurityService,
    public naming: NamingService
  ) { }

  ngOnInit() {
  }

  get user(): User {
    return this.service.user;
  }

  refresh() {
    this.service.refresh();
  }

}
