import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryUserDetailComponent } from './history-user-detail.component';

describe('HistoryUserDetailComponent', () => {
  let component: HistoryUserDetailComponent;
  let fixture: ComponentFixture<HistoryUserDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryUserDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryUserDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
