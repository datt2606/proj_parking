import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudService } from '@core/services/crud.service';
import { Vehicle } from 'app/models/vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService extends CrudService<Vehicle> {

  constructor(protected _http: HttpClient) { super(_http, "vehicle") }
}
