import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NamingService } from './../_service/naming.service';
import { SecurityService } from './../_service/security.service';
import { NzMessageService } from 'ng-zorro-antd';
import { RoleService } from './../_service/role.service';
import { UserService } from './../_service/user.service';
import { Role } from './../_model/role';
import { User } from './../_model/user';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  elements: User[] = [];
  show: User[] = [];
  roles: Role[] = [];
  _role: User = new User();

  sortValue = { key: null, value: null };
  loading = false;
  loadingRoles = false;
  adding = false;
  deleting = [];

  editIndex = -1;
  validating = [];

  searchString = '';

  addForm: FormGroup = this.builder.group({
    lastName: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    firstName: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    address: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    function: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    email: this.builder.control('', [Validators.required, Validators.email]),
    password: this.builder.control('', [Validators.required, Validators.minLength(6)]),
  });

  editForm: FormGroup = this.builder.group({
    lastName: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    firstName: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    address: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    function: this.builder.control('', [Validators.required, Validators.minLength(2)]),
  });

  constructor(
    private userService: UserService,
    private categoryService: RoleService,
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
    this.loadUsers();
  }

  loadUsers() {
    this.loading = true;
    this.userService.findAll().subscribe(elements => {
      this.elements = elements;
      this.filter();
      this.loading = false;
    });
  }

  loadRoles() {
    this.loadingRoles = true;
    this.categoryService.findAll().subscribe(elements => {
      this.roles = elements;
      this.loadingRoles = false;
    });
  }

  add() {
    this.adding = true;
    const user: User = { ...this.addForm.value };
    this.userService.save(user).subscribe(() => {
      this.message.success(
        `L'employé '${user.firstName} ${user.lastName}' a été ajouté.`
      );
      this.addForm.reset();
      this.adding = false;
      this.load();
    });
  }

  edit(element: User) {
    this.editIndex = element.id;
    this.editForm.reset();
    this.editForm.setValue({
      lastName: element.lastName,
      firstName: element.firstName,
      address: element.address,
      function: element.function,
    });
  }

  cancel() {
    this.editIndex = -1;
  }

  validate(element: User) {
    this.validating[element.id] = true;
    const user = { ...this.editForm.value };
    this.userService.edit(user).subscribe(
      () => {
        this.message.success(
          `L'employé '${user.firstName} ${
          user.lastName
          }' a été modifié.`
        );
        this.validating[element.id] = false;
        this.load();
        this.cancel();
      },
      response => {
        this.validating[element.id] = false;
        this.message.error(response.error.message);
      }
    );
  }

  delete(element: User) {
    const id = element.id;
    this.deleting[id] = true;
    this.userService.delete(id).subscribe(
      () => {
        this.message.success(
          `L'employé '${element.firstName} ${
          element.lastName
          }' a été supprimée.`
        );
        this.deleting[id] = false;
        this.load();
      },
      response => {
        this.deleting[id] = false;
        this.message.error(response.error.message);
      }
    );
  }

  compare(one: Role, tow: Role) {
    return one && tow && one.id === tow.id;
  }

  filter() {
    this.search();
    this.sort();
  }

  search() {
    this.show = this.elements.filter(element => {
      return (
        element.firstName.includes(this.searchString) ||
        element.lastName.includes(this.searchString) ||
        element.address.includes(this.searchString) ||
        element.email.includes(this.searchString) ||
        element.function.includes(this.searchString)
      );
    });
  }

  setSort(value) {
    this.sortValue = value;
    this.sort();
  }

  sort() {
    switch (this.sortValue.key) {
      case 'first':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.firstName.toLowerCase().localeCompare(b.firstName.toLowerCase()))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.firstName.toLowerCase().localeCompare(a.firstName.toLowerCase()))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'last':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.lastName.toLowerCase().localeCompare(b.lastName.toLowerCase()))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.lastName.toLowerCase().localeCompare(a.lastName.toLowerCase()))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'function':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.function.toLowerCase().localeCompare(b.function.toLowerCase()))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.function.toLowerCase().localeCompare(a.function.toLowerCase()))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
    }
  }
}
