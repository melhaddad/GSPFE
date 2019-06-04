import { NumberValidators } from './../_utils/number-validators';
import { FormControl, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { NamingService } from './../_service/naming.service';
import { Supply } from './../_model/supply';
import { Category } from './../_model/category';
import { CategoryService } from './../_service/category.service';
import { NzMessageService } from 'ng-zorro-antd';
import { ItemService } from './../_service/item.service';
import { Item } from './../_model/item';
import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../_service/security.service';

@Component({
  selector: 'app-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent implements OnInit {
  elements: Item[] = [];
  show: Item[] = [];
  categories: Category[] = [];

  editIndex = -1;

  sortValue = { key: null, value: null };
  loading = false;
  loadingCategories = false;
  adding = false;
  deleting = [];
  suppliyng = [];
  validating = [];
  searchString = '';

  addForm: FormGroup = this.builder.group({
    name: this.builder.control('', [Validators.required, Validators.maxLength(30), Validators.minLength(2)]),
    description: this.builder.control('', [Validators.required, Validators.maxLength(150), Validators.minLength(2)]),
    quantity: this.builder.control(1, [Validators.required, Validators.max(999), Validators.min(1)]),
    info: this.builder.control(50, [Validators.required, Validators.max(100), Validators.min(0)]),
    warning: this.builder.control(75, [Validators.required, Validators.max(100), Validators.min(0)]),
    error: this.builder.control(90, [Validators.required, Validators.max(100), Validators.min(0)]),
    category: this.builder.control(null, Validators.required)
  });

  editForm: FormGroup = this.builder.group({
    name: this.builder.control('', [Validators.required, Validators.maxLength(30), Validators.minLength(2)]),
    description: this.builder.control('', [Validators.required, Validators.maxLength(150), Validators.minLength(2)]),
    info: this.builder.control('', [Validators.required, Validators.max(100), Validators.min(0)]),
    warning: this.builder.control('', [Validators.required, Validators.max(100), Validators.min(0)]),
    error: this.builder.control('', [Validators.required, Validators.max(100), Validators.min(0)]),
    category: this.builder.control('', Validators.required)
  });

  constructor(
    private service: ItemService,
    private categoryService: CategoryService,
    public security: SecurityService,
    public naming: NamingService,
    private message: NzMessageService,
    private builder: FormBuilder
  ) { }

  ngOnInit() {
    this.load();
  }

  load() {
    this.loadCategories();
    this.loadItems();
  }

  loadItems() {
    this.loading = true;
    this.service.findAll().subscribe(elements => {
      this.elements = elements;
      this.filter();
      this.loading = false;
    });
  }

  loadCategories() {
    this.loadingCategories = true;
    this.categoryService.findAll().subscribe(elements => {
      this.categories = elements;
      this.loadingCategories = false;
    });
  }

  add() {
    this.adding = true;
    const item: Item = { id: null, ...this.addForm.value };
    this.service.save(item).subscribe(() => {
      this.message.success(`Le matériel '${item.name}' a été ajouté.`);
      this.addForm.reset();
      this.adding = false;
      this.load();
    });
  }

  edit(element: Item) {
    this.editForm.reset();

    this.editIndex = element.id;

    this.editForm.setValue({
      name: element.name,
      description: element.description,
      info: element.info,
      warning: element.warning,
      error: element.error,
      category: element.category
    });
  }

  validate(element: Item) {
    this.validating[element.id] = true;
    const item: Item = { id: element.id, ...this.editForm.value };
    this.service.edit(item).subscribe(
      () => {
        this.message.success(`Le matériel '${element.name}' a été modifié.`);
        this.editForm.reset();
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

  delete(element: Item) {
    const id = element.id;
    this.deleting[id] = true;
    this.service.delete(id).subscribe(
      () => {
        this.deleting[id] = false;
        this.message.success(`Le matériel '${element.name}' a été supprimée.`);
        this.load();
      },
      response => {
        this.deleting[id] = false;
        this.message.error(response.error.message);
      }
    );
  }

  compare(one: Category, tow: Category) {
    return one && tow && one.id === tow.id;
  }

  filter() {
    this.search();
    this.sort();
  }

  search() {
    this.show = this.elements.filter(element => {
      return (
        element.name.includes(this.searchString) ||
        element.description.includes(this.searchString) ||
        element.quantity.toString().includes(this.searchString) ||
        element.info.toString().includes(this.searchString) ||
        element.warning.toString().includes(this.searchString) ||
        element.error.toString().includes(this.searchString) ||
        element.category.name.includes(this.searchString)
      );
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
          this.show = [...this.show.sort((a, b) => a.name.toLowerCase().localeCompare(b.name.toLowerCase()))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.name.toLowerCase().localeCompare(a.name.toLowerCase()))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'category':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.category.name.localeCompare(b.category.name))];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.category.name.localeCompare(a.category.name))];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'quantity':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.quantity > b.quantity ? 1 : -1)];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.quantity > a.quantity ? 1 : -1)];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'info':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.info > b.info ? 1 : -1)];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.info > a.info ? 1 : -1)];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'warning':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.warning > b.warning ? 1 : -1)];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.warning > a.warning ? 1 : -1)];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
      case 'error':
        if (this.sortValue.value === 'ascend') {
          this.show = [...this.show.sort((a, b) => a.error > b.error ? 1 : -1)];
        } else if (this.sortValue.value === 'descend') {
          this.show = [...this.show.sort((a, b) => b.error > a.error ? 1 : -1)];
        } else {
          this.show = [...this.elements];
          this.search();
        }
        break;
    }
  }

  validateNumber(event) {
    const pattern = /[0-9]/;
    const inputChar = String.fromCharCode(event.charCode);
    if (event.keyCode !== 8 && !pattern.test(inputChar)) {
      event.preventDefault();
    }
  }
}
