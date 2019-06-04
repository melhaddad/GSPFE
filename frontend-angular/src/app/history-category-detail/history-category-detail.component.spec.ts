import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryCategoryDetailComponent } from './history-category-detail.component';

describe('HistoryCategoryDetailComponent', () => {
  let component: HistoryCategoryDetailComponent;
  let fixture: ComponentFixture<HistoryCategoryDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoryCategoryDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoryCategoryDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
