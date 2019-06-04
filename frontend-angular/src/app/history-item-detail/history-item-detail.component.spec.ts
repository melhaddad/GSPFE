import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryItemDetailComponent } from './history-item-detail.component';

describe('HistoryItemDetailComponent', () => {
  let component: HistoryItemDetailComponent;
  let fixture: ComponentFixture<HistoryItemDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryItemDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryItemDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
