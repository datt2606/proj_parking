import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { VehicleService } from '../vehicle.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Vehicle } from 'app/models/vehicle';
import { ToastrService } from 'ngx-toastr';
import { VehicleCategoryService } from 'app/main/vehicle-category/vehicle-category.service';
import { VehicleCategory } from 'app/models/vehicle-category';
import { AuthenticationService } from 'app/auth/service';

@Component({
  selector: 'app-create-update',
  templateUrl: './create-update.component.html',
  styleUrls: ['./create-update.component.scss']
})
export class CreateUpdateComponent implements OnInit {
  @Input("modal") public modal: NgbActiveModal;
  @Input("vehicle") public vehicle: Vehicle;
  @Output("success") public success = new EventEmitter<boolean>();
  public form: FormGroup
  public submitted = false
  public vehicleCategoryItems: VehicleCategory[] = []

  get f() {
    return this.form.controls
  }

  constructor(private _fb: FormBuilder, private _toastrService: ToastrService, private _vehicleService: VehicleService, private _vehicleCategoryService: VehicleCategoryService, private _authService: AuthenticationService) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      vehicleName: [null, Validators.required],
      color: [null, Validators.required],
      licensePlate: [null, Validators.required],
      manufacturer: [null, Validators.required],
      customerId: [this._authService.currentUserValue.id, Validators.required],
      categoryId: [null, Validators.required],
    })

    if (this.vehicle)
      this.form.patchValue({ ...this.vehicle, categoryId: this.vehicle.vehicleCategory.id })

    this._vehicleCategoryService.getAll().subscribe(res => {
      this.vehicleCategoryItems = res.data
    })
  }


  onSubmit() {
    this.submitted = true
    if (this.form.invalid) return

    let observable = this._vehicleService.create(this.form.value);
    if (this.vehicle)
      observable = this._vehicleService.update(
        this.form.value,
        this.vehicle.id
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
