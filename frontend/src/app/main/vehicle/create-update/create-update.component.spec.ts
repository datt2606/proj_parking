import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAndUpdateComponent } from './create-update.component';

describe('CreateAndUpdateComponent', () => {
  let component: CreateAndUpdateComponent;
  let fixture: ComponentFixture<CreateAndUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAndUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAndUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
