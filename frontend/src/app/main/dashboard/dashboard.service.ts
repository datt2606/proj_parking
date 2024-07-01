import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Response } from "app/models/response";
import { environment } from "environments/environment";

@Injectable({
  providedIn: "root",
})
export class DashboardService {
  constructor(private _http: HttpClient) {}

  getUserWithAge() {
    return this._http.get<Response<any[]>>(
      `${environment.apiUrl}/dashboard/user-with-age`
    );
  }

  getRevenue() {
    return this._http.get<Response<any>>(
      `${environment.apiUrl}/dashboard/revenue?year=2024`
    );
  }

  getParkingTotal() {
    return this._http.get<Response<any>>(
      `${environment.apiUrl}/dashboard/parking-total?year=2024`
    );
  }
}
