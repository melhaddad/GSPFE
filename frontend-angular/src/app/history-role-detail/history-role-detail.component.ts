import { Role } from './../_model/role';
import { History } from './../_model/history';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-history-role-detail',
  templateUrl: './history-role-detail.component.html',
  styleUrls: ['./history-role-detail.component.css']
})
export class HistoryRoleDetailComponent implements OnInit {
  @Input()
  element: History<Role> = new History<Role>();

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
}
