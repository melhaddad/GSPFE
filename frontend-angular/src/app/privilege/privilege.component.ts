import { ProfileService } from './../_service/profile.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NamingService } from './../_service/naming.service';
import { NzMessageService } from 'ng-zorro-antd';
import { PrivilegeService } from './../_service/privilege.service';
import { Privilege } from './../_model/privilege';
import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../_service/security.service';

@Component({
  selector: 'app-privilege',
  templateUrl: './privilege.component.html',
  styleUrls: ['./privilege.component.css']
})
export class PrivilegeComponent implements OnInit {
  elements: Privilege[] = [];
  show: Privilege[] = [];

  sortValue = { key: null, value: null };
  loading = false;

  editIndex = -1;

  activing = [];
  validating = [];
  searchString = '';

  form: FormGroup = this.builder.group({
    description: this.builder.control('', [Validators.required, Validators.minLength(2)]),
    show: this.builder.control('', [Validators.required, Validators.minLength(2)])
  });

  constructor(
    private service: PrivilegeService,
    private profileService: ProfileService,
    public security: SecurityService,
    public naming: NamingService,
    private message: NzMessageService,
    private builder: FormBuilder
  ) { }

  ngOnInit() {
    this.load();
  }

  load() {
    this.loading = true;
    this.service.findAll().subscribe(elements => {
      this.elements = elements;
      this.filter();
      this.profileService.refresh();
      this.loading = false;
    });
  }

  edit(element: Privilege) {
    this.form.reset();

    this.editIndex = element.id;

    this.form.setValue({ description: element.description, show: element.show });
  }

  validate(element: Privilege) {
    this.validating[element.id] = true;
    const privilege = { ...element, description: this.form.controls['description'].value, show: this.form.controls['show'].value };
    this.service.edit(privilege).subscribe(() => {
      this.message.success(`Le privilège '${privilege.name}' a été modifié.`);
      this.load();
      this.service.load();
      this.cancel();
      this.validating[element.id] = false;
    }, response => {
      this.validating[element.id] = false;
      this.message.error(response.error.message);
    });
  }

  cancel() {
    this.editIndex = -1;
  }

  disable(element: Privilege) {
    const id = element.id;
    this.activing[id] = true;
    this.service.disable(element).subscribe(() => {
      this.activing[id] = false;
      this.message.success(`Le privilège '${element.name}' a été désactivé.`);
      this.load();
    }, response => {
      this.activing[id] = false;
      this.message.error(response.error.message);
    });
  }

  enable(element: Privilege) {
    const id = element.id;
    this.activing[id] = true;
    this.service.enable(element).subscribe(() => {
      this.activing[id] = false;
      this.message.success(`Le privilège '${element.name}' a été activé.`);
      this.load();
    }, response => {
      this.activing[id] = false;
      this.message.error(response.error.message);
    });
  }

  filter() {
    this.search();
    this.sort();
  }

  search() {
    this.show = this.elements.filter(element => {
      return element.name.includes(this.searchString) ||
        element.description.includes(this.searchString) ||
        element.show.includes(this.searchString);
    });
  }

  setSort(value) {
    this.sortValue = value;
    this.sort();
  }

  sort() {
    switch (this.sortValue.key) {
      case 'name':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.name.localeCompare(b.name))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.name.localeCompare(a.name))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'description':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.description.toLowerCase().localeCompare(b.description.toLowerCase()))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.description.toLowerCase().localeCompare(a.description.toLowerCase()))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'show':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.show.toLowerCase().localeCompare(b.show.toLowerCase()))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.show.toLowerCase().localeCompare(a.show.toLowerCase()))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'state':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.active ? 1 : b.active ? -1 : 0)];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.active ? 1 : a.active ? -1 : 0)];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
    }
  }
}
