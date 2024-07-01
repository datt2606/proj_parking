import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudService } from '@core/services/crud.service';
import { VehicleCategory } from 'app/models/vehicle-category';

@Injectable({
  providedIn: 'root'
})
export class VehicleCategoryService extends CrudService<VehicleCategory> {

  constructor(protected _http: HttpClient) {
    super(_http, "vehicle-category")
  }
}
