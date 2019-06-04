import { History } from './../_model/history';
import { Category } from './../_model/category';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-history-category-detail',
  templateUrl: './history-category-detail.component.html',
  styleUrls: ['./history-category-detail.component.css']
})
export class HistoryCategoryDetailComponent implements OnInit {
  @Input()
  element: History<Category> = new History<Category>();

  @Input()
  action: string;

  constructor() { }

  ngOnInit() {
  }

  get isUpdate() {
    return this.element.type === 1;
  }

  get isNameChanged(): boolean {
    return this.isUpdate && this.element.current.name !== this.element.previous.name;
  }

  get isDescriptionChanged(): boolean {
    return this.isUpdate && this.element.current.description !== this.element.previous.description;
  }

}
