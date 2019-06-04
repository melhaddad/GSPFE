import { History } from './../_model/history';
import { HistoryService } from './../_service/history.service';
import { NamingService } from './../_service/naming.service';
import { NzMessageService } from 'ng-zorro-antd';
import { SecurityService } from './../_service/security.service';
import { AuthService } from './../_service/auth.service';
import { ItemService } from './../_service/item.service';
import { AssignmentService } from './../_service/assignment.service';
import { Item } from './../_model/item';
import { Assignment } from './../_model/assignment';
import { Component, OnInit } from '@angular/core';
import { HistoryType } from '../_model/history-type.enum';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  idx = 0;
  elements: any[] = [];
  loading = false;
  page = 1;
  pageSize = 10;

  constructor(
    private service: HistoryService,
    public security: SecurityService,
    public naming: NamingService,
    private message: NzMessageService
  ) { }


  ngOnInit() {
    this.load();
  }

  get url() {
    switch (this.idx) {
      case 0: return 'categories';
      case 1: return 'items';
      case 2: return 'users';
      case 3: return 'users_items';
      case 4: return 'roles';
      case 5: return 'privileges';
    }
  }

  get pages(): number {
    return this.elements.length;
  }

  load() {
    this.page = 1;
    this.elements = [];
    this.loading = true;
    this.service.findAll(this.url).subscribe(data => {
      this.loadElements(data);
      this.loading = false;
    });
  }

  show(element) {
    return JSON.stringify(element.content[0].entity);
  }

  loadElements(data) {
    this.elements = [];
    for (let index = 0; index < data.length; index++) {
      const content = data[index].content;
      for (let j = 0; j < content.length; j++) {
        const element = content[j];
        const item: History<any> = new History<any>();
        item.id = element.revisionNumber;
        item.date = new Date(
          element.metadata.delegate.timestamp
        ).toLocaleString();
        item.current = element.entity;
        if (j === 0) {
          item.previous = null;
          item.type = HistoryType.CREATED;
          item.user = item.current.createdBy;
          if (item.user === null) { item.user = 'Système'; }
        } else {
          if (
            item.current.deleted === undefined ||
            item.current.deleted === false
          ) {
            item.type = HistoryType.UPDATED;
          } else {
            item.type = HistoryType.DELETED;
          }
          item.previous = content[j - 1].entity;
          item.user = item.current.modifiedBy;
        }
        this.elements.push(item);
      }
    }
    this.elements.sort((a, b) => -a.id + b.id);
  }
}
