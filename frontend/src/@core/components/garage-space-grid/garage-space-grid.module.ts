import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GarageSpaceGridComponent } from './garage-space-grid.component';



@NgModule({
  declarations: [
    GarageSpaceGridComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [GarageSpaceGridComponent]
})
export class GarageSpaceGridModule { }
