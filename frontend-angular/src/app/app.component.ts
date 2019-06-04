import { AuthService } from './_service/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isCollapsed: Boolean = false;
  width: Number = 200;

  constructor(public authService: AuthService) {}

  toggle() {
    this.isCollapsed = !this.isCollapsed;
  }
}
