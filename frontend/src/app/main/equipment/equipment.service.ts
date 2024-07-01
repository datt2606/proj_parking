import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudService } from '@core/services/crud.service';
import { Equipment } from 'app/models/equipment';

@Injectable({
  providedIn: 'root'
})
export class EquipmentService extends CrudService<Equipment> {

  constructor(protected _http: HttpClient) {
    super(_http, "equipment")
  }
}
