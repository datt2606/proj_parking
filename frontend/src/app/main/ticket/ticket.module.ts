import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule, Routes } from "@angular/router";
import { TicketComponent } from "./ticket.component";
import { GarageSpaceGridModule } from "@core/components/garage-space-grid/garage-space-grid.module";
import { CoreCommonModule } from "@core/common.module";
import { DataTableFilterModule } from "@core/components/data-table-filter/data-table-filter.module";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { CreateUpdateComponent } from './create-update/create-update.component';

const routes: Routes = [
  {
    path: "",
    component: TicketComponent,
  },
];

@NgModule({
  declarations: [TicketComponent, CreateUpdateComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CoreCommonModule,
    DataTableFilterModule,
    NgbModule,
    GarageSpaceGridModule,
  ],
})
export class TicketModule {}
