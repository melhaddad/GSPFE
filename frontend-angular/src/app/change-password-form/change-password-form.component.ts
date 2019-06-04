import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { ProfileService } from '../_service/profile.service';
import { NamingService } from '../_service/naming.service';
import { NzMessageService } from 'ng-zorro-antd';
import { User } from '../_model/user';

@Component({
  selector: 'app-change-password-form',
  templateUrl: './change-password-form.component.html',
  styleUrls: ['./change-password-form.component.css']
})
export class ChangePasswordFormComponent implements OnInit {
  loading = false;

  form: FormGroup = this.builder.group({
    password: this.builder.control('', [Validators.required, Validators.minLength(6)])
  });

  constructor(
    private service: ProfileService,
    public naming: NamingService,
    private message: NzMessageService,
    private builder: FormBuilder
  ) { }

  ngOnInit() { }

  change() {
    this.loading = true;
    const user: User = { ...this.service.user, password: this.form.controls['password'].value };
    this.service.changePassword(user).subscribe(
      () => {
        this.form.reset();
        this.loading = false;
        this.message.success('Le mot de passe a été changé.');
      },
      response => {
        this.loading = false;
        this.message.error(response.error.message);
      }
    );
  }
}
