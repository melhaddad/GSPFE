import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemSupplyFormComponent } from './item-supply-form.component';

describe('ItemSupplyFormComponent', () => {
  let component: ItemSupplyFormComponent;
  let fixture: ComponentFixture<ItemSupplyFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemSupplyFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemSupplyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
