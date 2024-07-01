import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudService } from '@core/services/crud.service';
import { Space } from 'app/models/space';

@Injectable({
  providedIn: 'root'
})
export class SpaceService extends CrudService<Space> {

  constructor(protected _http: HttpClient) {
    super(_http, "parking-space")
  }
}
