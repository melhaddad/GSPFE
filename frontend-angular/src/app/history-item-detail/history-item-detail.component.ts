import { Item } from './../_model/item';
import { History } from './../_model/history';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-history-item-detail',
  templateUrl: './history-item-detail.component.html',
  styleUrls: ['./history-item-detail.component.css']
})
export class HistoryItemDetailComponent implements OnInit {
  @Input()
  element: History<Item> = new History<Item>();

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

  get isQuantityChanged(): boolean {
    return this.isUpdate && this.element.current.quantity !== this.element.previous.quantity;
  }

  get isInfoChanger(): boolean {
    return this.isUpdate && this.element.current.info !== this.element.previous.info;
  }

  get isWarningChanged(): boolean {
    return this.isUpdate && this.element.current.warning !== this.element.previous.warning;
  }

  get isErrorChanged(): boolean {
    return this.isUpdate && this.element.current.error !== this.element.previous.error;
  }
}
