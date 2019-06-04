import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeImageFormComponent } from './change-image-form.component';

describe('ChangeImageFormComponent', () => {
  let component: ChangeImageFormComponent;
  let fixture: ComponentFixture<ChangeImageFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeImageFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeImageFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
