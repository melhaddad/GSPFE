import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryRoleDetailComponent } from './history-role-detail.component';

describe('HistoryRoleDetailComponent', () => {
  let component: HistoryRoleDetailComponent;
  let fixture: ComponentFixture<HistoryRoleDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryRoleDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryRoleDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
