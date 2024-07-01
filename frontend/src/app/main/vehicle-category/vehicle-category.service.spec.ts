import { TestBed } from '@angular/core/testing';

import { VehicleCategoryService } from './vehicle-category.service';

describe('VehicleCategoryService', () => {
  let service: VehicleCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehicleCategoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
