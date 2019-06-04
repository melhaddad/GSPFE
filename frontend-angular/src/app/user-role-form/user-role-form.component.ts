import { ProfileService } from './../_service/profile.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RoleService } from './../_service/role.service';
import { UserService } from './../_service/user.service';
import { User } from './../_model/user';
import { NzMessageService } from 'ng-zorro-antd';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Role } from '../_model/role';

@Component({
  selector: 'app-user-role-form',
  templateUrl: './user-role-form.component.html',
  styleUrls: ['./user-role-form.component.css']
})
export class UserRoleFormComponent implements OnInit {
  @Input()
  user: User = new User();

  @Input()
  roles: Role[];

  @Output()
  load: EventEmitter<any> = new EventEmitter();

  loading = false;

  form: FormGroup = this.builder.group({
    roles: this.builder.control(null, Validators.required)
  });

  constructor(
    private service: UserService,
    private builder: FormBuilder,
    private message: NzMessageService,
    private profileService: ProfileService
  ) { }

  ngOnInit() {
    this.form.controls['roles'].setValue(this.user.roles);
  }

  updateRoles() {
    this.loading = true;
    const user = { ...this.user, roles: this.form.controls['roles'].value };
    this.service.roles(user).subscribe(() => {
      this.message.success(
        `Les rôles de l'employé '${user.firstName} ${user.lastName}' ont été modifié.`
      );
      this.loading = false;
      this.profileService.refresh();
      this.load.emit(null);
    }, response => {
      this.loading = false;
      this.message.error(response.error.message);
    });
  }

  compare(one: Role, tow: Role) {
    return one && tow && one.id === tow.id;
  }

}
