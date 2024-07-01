import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudService } from '@core/services/crud.service';
import { Garage } from 'app/models/garage';

@Injectable({
  providedIn: 'root'
})
export class GarageService extends CrudService<Garage> {

  constructor(protected _http: HttpClient) {
    super(_http, "garage")
  }
}
