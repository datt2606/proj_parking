import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";

import { CoreCommonModule } from "@core/common.module";
import { NgSelectModule } from "@ng-select/ng-select";
import { ReactiveFormsModule } from "@angular/forms";
import { Ng2FlatpickrModule } from "ng2-flatpickr";
import { DataTableFilterComponent } from "./data-table-filter.component";

@NgModule({
  declarations: [DataTableFilterComponent],
  imports: [
    CommonModule,
    CoreCommonModule,
    NgSelectModule,
    ReactiveFormsModule,
    Ng2FlatpickrModule,
  ],
  exports: [DataTableFilterComponent],
})
export class DataTableFilterModule { }
