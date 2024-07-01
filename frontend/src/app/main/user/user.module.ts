import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from './user.component';
import { RouterModule, Routes } from '@angular/router';
import { DataTableFilterModule } from '@core/components/data-table-filter/data-table-filter.module';
import { CoreCommonModule } from '@core/common.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CreateUpdateComponent } from './create-update/create-update.component';
import { Ng2FlatpickrModule } from 'ng2-flatpickr';

const routes: Routes = [
  {
    path: '',
    component: UserComponent
  }
]

@NgModule({
  declarations: [
    UserComponent,
    CreateUpdateComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    CoreCommonModule,
    DataTableFilterModule,
    NgbModule,
    Ng2FlatpickrModule
  ]
})
export class UserModule { }
