import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfileService } from './../_service/profile.service';
import { AuthService } from '../_service/auth.service';
import { UserCredentials } from '../_model/user-credentials';
import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loading = false;

  form: FormGroup = this.builder.group({
    username: this.builder.control('inventorymanagement@ilemgroup.com', [Validators.required, Validators.email]),
    password: this.builder.control('password', [Validators.required, Validators.minLength(6)]),
  });

  constructor(
    private authService: AuthService,
    private messageService: NzMessageService,
    private router: Router,
    private profileService: ProfileService,
    private builder: FormBuilder
  ) { }

  ngOnInit() {
  }

  submitForm(): void {
    this.loading = true;
    this.authService
      .login(this.form.value)
      .then(() => {
        this.profileService.refresh();
        this.router.navigateByUrl('/assignments');
      })
      .catch(() => {
        this.messageService.error('Failed to log in.');
        this.loading = false;
      });
  }
}
