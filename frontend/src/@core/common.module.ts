import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CoreDirectivesModule } from '@core/directives/directives';
import { CorePipesModule } from '@core/pipes/pipes.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxDatatableModule } from "@swimlane/ngx-datatable";

@NgModule({
  imports: [CommonModule, FlexLayoutModule, FormsModule, ReactiveFormsModule, CoreDirectivesModule, CorePipesModule, NgxDatatableModule, NgSelectModule],
  exports: [CommonModule, FlexLayoutModule, FormsModule, ReactiveFormsModule, CoreDirectivesModule, CorePipesModule, NgxDatatableModule, NgSelectModule]
})
export class CoreCommonModule { }
