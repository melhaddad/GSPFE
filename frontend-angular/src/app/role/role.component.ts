import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SecurityService } from './../_service/security.service';
import { NzMessageService } from 'ng-zorro-antd';
import { RoleService } from './../_service/role.service';
import { Component, OnInit } from '@angular/core';
import { Role } from '../_model/role';
import { Privilege } from '../_model/privilege';
import { PrivilegeService } from '../_service/privilege.service';
import { NamingService } from '../_service/naming.service';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class RoleComponent implements OnInit {
  elements: Role[] = [];
  show: Role[] = [];
  privileges: Privilege[] = [];

  sortValue = { key: null, value: null };
  loading = false;
  adding = false;
  deleting = [];

  editIndex = -1;

  validating = [];
  searchString = '';

  addForm: FormGroup = this.builder.group({
    name: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    privileges: this.builder.control(null, Validators.required)
  });

  editForm: FormGroup = this.builder.group({
    name: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    privileges: this.builder.control(null, Validators.required)
  });

  constructor(
    private roleService: RoleService,
    private privilegeService: PrivilegeService,
    public security: SecurityService,
    public naming: NamingService,
    private message: NzMessageService,
    private builder: FormBuilder
  ) { }

  ngOnInit() {
    this.load();
  }

  load() {
    this.loadRoles();
    this.loadPrivileges();
  }

  loadRoles() {
    this.loading = true;
    this.roleService.findAll().subscribe(elements => {
      this.elements = elements;
      this.filter();
      this.loading = false;
    });
  }

  loadPrivileges() {
    this.loading = true;
    this.privilegeService.findAll().subscribe(elements => {
      this.privileges = elements;
      this.loading = false;
    });
  }

  reset() {
    this.addForm.reset();
  }

  add() {
    this.adding = true;
    const role: Role = this.addForm.value;
    this.roleService.save(role).subscribe(() => {
      this.message.success(`Le rôle '${role.name}' a été ajouté.`);
      this.load();
      this.reset();
      this.adding = false;
    });
  }

  edit(element: Role) {
    this.editIndex = element.id;

    this.editForm.reset();

    this.editForm.setValue({
      name: element.name,
      privileges: element.privileges
    });
  }

  validate(element: Role) {
    this.validating[element.id] = true;
    const role: Role = { ...element, name: this.editForm.controls['name'].value, privileges: this.editForm.controls['privileges'].value };
    this.roleService.edit(role).subscribe(
      () => {
        this.message.success(`Le rôle '${element.name}' a été modifié.`);
        this.load();
        this.cancel();
        this.validating[element.id] = false;
      },
      response => {
        this.validating[element.id] = false;
        this.message.error(response.error.message);
      }
    );
  }

  cancel() {
    this.editIndex = -1;
  }

  delete(element: Role) {
    const id = element.id;
    this.deleting[id] = true;
    this.roleService.delete(id).subscribe(
      () => {
        this.deleting[id] = false;
        this.message.success(`Le rôle '${element.name}' a été supprimée.`);
        this.load();
      },
      response => {
        this.deleting[id] = false;
        this.message.error(response.error.message);
      }
    );
  }

  compare(one: Privilege, tow: Privilege): boolean {
    return one && tow && one.id === tow.id;
  }

  filter() {
    this.search();
    this.sort();
  }

  search() {
    this.show = this.elements.filter(element =>
      element.name.includes(this.searchString)
    );
  }

  setSort(value) {
    this.sortValue = value;
    this.sort();
  }

  sort() {
    switch (this.sortValue.key) {
      case 'name':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.name.toLowerCase().localeCompare(b.name.toLowerCase()))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.name.toLowerCase().localeCompare(a.name.toLowerCase()))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
    }
  }
}
