import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryAssignmentDetailComponent } from './history-assignment-detail.component';

describe('HistoryAssignmentDetailComponent', () => {
  let component: HistoryAssignmentDetailComponent;
  let fixture: ComponentFixture<HistoryAssignmentDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryAssignmentDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryAssignmentDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
