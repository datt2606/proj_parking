import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CrudService } from "@core/services/crud.service";
import { Ticket } from "app/models/ticket";

@Injectable({
  providedIn: "root",
})
export class TicketService extends CrudService<Ticket> {
  constructor(protected _http: HttpClient) {
    super(_http, "parking-ticket");
  }

  active(ticketId: number) {
    return this._http.put(`${this._path}/active/${ticketId}`, {});
  }

  parking(ticketId: number) {
    return this._http.put(`${this._path}/parking/${ticketId}`, {});
  }
}
