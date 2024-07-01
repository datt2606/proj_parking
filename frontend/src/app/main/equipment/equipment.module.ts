import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EquipmentComponent } from './equipment.component';
import { Routes, RouterModule } from '@angular/router';
import { CoreCommonModule } from '@core/common.module';
import { DataTableFilterModule } from '@core/components/data-table-filter/data-table-filter.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CreateUpdateComponent } from './create-update/create-update.component';

const routes: Routes = [
  {
    path: '',
    component: EquipmentComponent
  }
]

@NgModule({
  declarations: [
    EquipmentComponent,
    CreateUpdateComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CoreCommonModule,
    DataTableFilterModule,
    NgbModule,
  ]
})
export class EquipmentModule { }
