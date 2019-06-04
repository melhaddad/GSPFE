import { History } from './../_model/history';
import { Assignment } from './../_model/assignment';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-history-assignment-detail',
  templateUrl: './history-assignment-detail.component.html',
  styleUrls: ['./history-assignment-detail.component.css']
})
export class HistoryAssignmentDetailComponent implements OnInit {
  @Input()
  element: History<Assignment> = new History<Assignment>();

  @Input()
  action: string;

  constructor() { }

  ngOnInit() {
  }

  get isUpdate() {
    return this.element.type === 1;
  }

  get isQuantityChanged(): boolean {
    return this.isUpdate && this.element.current.quantity !== this.element.previous.quantity;
  }

  get isStateChanged(): boolean {
    return this.isUpdate && this.element.current.type !== this.element.previous.type;
  }

}
