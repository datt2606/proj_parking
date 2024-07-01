import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GarageSpaceGridComponent } from './garage-space-grid.component';

describe('GarageSpaceGridComponent', () => {
  let component: GarageSpaceGridComponent;
  let fixture: ComponentFixture<GarageSpaceGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GarageSpaceGridComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GarageSpaceGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
