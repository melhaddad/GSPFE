import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryPrivilegeDetailComponent } from './history-privilege-detail.component';

describe('HistoryPrivilegeDetailComponent', () => {
  let component: HistoryPrivilegeDetailComponent;
  let fixture: ComponentFixture<HistoryPrivilegeDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryPrivilegeDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryPrivilegeDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
