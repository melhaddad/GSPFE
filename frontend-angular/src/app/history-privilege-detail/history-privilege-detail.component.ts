import { History } from './../_model/history';
import { Privilege } from './../_model/privilege';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-history-privilege-detail',
  templateUrl: './history-privilege-detail.component.html',
  styleUrls: ['./history-privilege-detail.component.css']
})
export class HistoryPrivilegeDetailComponent implements OnInit {
  @Input()
  element: History<Privilege> = new History<Privilege>();

  @Input()
  action: string;

  constructor() { }

  ngOnInit() {
  }

  get isUpdate() {
    return this.element.type === 1;
  }

  get isShowChanged(): boolean {
    return this.isUpdate && this.element.current.show !== this.element.previous.show;
  }

  get isDescriptionChanged(): boolean {
    return this.isUpdate && this.element.current.description !== this.element.previous.description;
  }

  get isActiveChanged(): boolean {
    return this.isUpdate && this.element.current.active !== this.element.previous.active;
  }
}
