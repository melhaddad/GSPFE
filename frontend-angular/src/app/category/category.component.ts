import { NamingService } from './../_service/naming.service';
import { Category } from './../_model/category';
import { CategoryService } from './../_service/category.service';
import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { SecurityService } from '../_service/security.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  elements: Category[] = [];
  show: Category[] = [];

  editIndex = -1;

  loading = false;
  adding = false;
  deleting = [];
  validating = [];

  searchString = '';
  sortValue = { key: null, value: null };

  addForm: FormGroup = this.builder.group({
    name: this.builder.control('', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    description: this.builder.control('', [Validators.required, Validators.minLength(2), Validators.maxLength(150)])
  });

  editForm: FormGroup = this.builder.group({
    name: this.builder.control('', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
    description: this.builder.control('', [Validators.required, Validators.minLength(2), Validators.maxLength(150)])
  });

  constructor(
    private service: CategoryService,
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
      this.loading = false;
    });
  }

  add() {
    this.adding = true;
    const category: Category = {
      id: null,
      name: this.addForm.controls['name'].value,
      description: this.addForm.controls['description'].value
    };

    this.service.save(category).subscribe(() => {
      this.message.success(`La catégorie '${category.name}' a été ajouté.`);
      this.addForm.reset();
      this.adding = false;
      this.load();
    }, response => {
      this.message.error(response.error.message);
      this.adding = false;
    });
  }

  edit(element: Category) {
    this.editForm.reset();
    this.editForm.controls['name'].setValue(element.name);
    this.editForm.controls['description'].setValue(element.description);
    this.editIndex = element.id;
  }

  validate(element: Category) {
    this.validating[element.id] = true;
    const category = {
      id: element.id,
      name: this.editForm.controls['name'].value,
      description: this.editForm.controls['description'].value
    };
    this.service.edit(category).subscribe(
      () => {
        this.message.success(`La catégorie '${element.name}' a été modifié.`);
        this.cancel();
        this.validating[element.id] = false;
        this.load();
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

  delete(element: Category) {
    const id = element.id;
    this.deleting[id] = true;
    this.service.delete(id).subscribe(
      () => {
        this.deleting[id] = false;
        this.message.success(`La catégorie '${element.name}' a été supprimée.`);
        this.load();
      },
      response => {
        this.deleting[id] = false;
        this.message.error(response.error.message);
      }
    );
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
