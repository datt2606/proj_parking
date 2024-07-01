import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { VehicleCategory } from 'app/models/vehicle-category';
import { VehicleCategoryService } from '../vehicle-category.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-update',
  templateUrl: './create-update.component.html',
  styleUrls: ['./create-update.component.scss']
})
export class CreateUpdateComponent implements OnInit {
  @Input("modal") public modal: NgbActiveModal;
  @Input("vehicleCategory") public vehicleCategory: VehicleCategory;
  @Output("success") public success = new EventEmitter<boolean>();
  public form: FormGroup
  public submitted = false

  get f() {
    return this.form.controls
  }

  constructor(private _fb: FormBuilder, private _toastrService: ToastrService, private _vehicleCategoryService: VehicleCategoryService) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      name: [null, Validators.required],
      description: [null, Validators.required],
    })

    if (this.vehicleCategory)
      this.form.patchValue(this.vehicleCategory)

  }


  onSubmit() {
    this.submitted = true
    if (this.form.invalid) return

    let observable = this._vehicleCategoryService.create(this.form.value);
    if (this.vehicleCategory)
      observable = this._vehicleCategoryService.update(
        this.form.value,
        this.vehicleCategory.id
      );

    observable.subscribe(() => {
      this._toastrService.success("", "Thành công", {
        positionClass: "toast-top-center",
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
      this.success.emit(true);
      this.modal.close();
    });
  }
}
