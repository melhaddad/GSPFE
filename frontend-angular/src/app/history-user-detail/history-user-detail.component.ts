import { User } from './../_model/user';
import { History } from './../_model/history';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-history-user-detail',
  templateUrl: './history-user-detail.component.html',
  styleUrls: ['./history-user-detail.component.css']
})
export class HistoryUserDetailComponent implements OnInit {
  @Input()
  element: History<User> = new History<User>();

  @Input()
  action: string;

  constructor() { }

  ngOnInit() {
  }

  get isUpdate() {
    return this.element.type === 1;
  }

  get isFirstNameChanged(): boolean {
    return this.isUpdate && this.element.current.firstName !== this.element.previous.firstName;
  }

  get isLastNameChanged(): boolean {
    return this.isUpdate && this.element.current.lastName !== this.element.previous.lastName;
  }

  get isAddressChanged(): boolean {
    return this.isUpdate && this.element.current.address !== this.element.previous.address;
  }

  get isFunctionChanger(): boolean {
    return this.isUpdate && this.element.current.function !== this.element.previous.function;
  }

  get isEmailChanged(): boolean {
    return this.isUpdate && this.element.current.email !== this.element.previous.email;
  }

  get isImageChanged(): boolean {
    return this.isUpdate && this.element.current.image !== this.element.previous.image;
  }
}
