import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CrudService } from '@core/services/crud.service';
import { User } from 'app/models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService extends CrudService<User> {

  constructor(protected _http: HttpClient) {
    super(_http, "user")
  }

  createEmployee(body) {
    return this._http.post(`${this._path}/create-employee`, body)
  }

  updateEmployee(body) {
    return this._http.put(`${this._path}/update-employee`, body)
  }
}
