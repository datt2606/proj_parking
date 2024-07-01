import { Space } from "./space";
import { User } from "./user";
import { Vehicle } from "./vehicle";

export class Ticket {
  id: number;
  customer: User;
  registrationDate: Date;
  expiryDate: Date;
  isActive: boolean;
  isParking: boolean;
  parkingSpace: Space;
  vehicle: Vehicle;
  ticketType: string;
}
