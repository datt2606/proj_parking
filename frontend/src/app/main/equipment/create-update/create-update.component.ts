import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Equipment } from 'app/models/equipment';
import { EquipmentService } from '../equipment.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { GarageService } from 'app/main/garage/garage.service';
import { ToastrService } from 'ngx-toastr';
import { Garage } from 'app/models/garage';

@Component({
  selector: 'app-create-update',
  templateUrl: './create-update.component.html',
  styleUrls: ['./create-update.component.scss']
})
export class CreateUpdateComponent implements OnInit {
  @Input("modal") public modal: NgbActiveModal;
  @Input("equipment") public equipment: Equipment;
  @Output("success") public success = new EventEmitter<boolean>();
  public form: FormGroup
  public submitted = false
  public garageItems: Garage[] = []

  get f() {
    return this.form.controls
  }

  constructor(private _fb: FormBuilder, private _toastrService: ToastrService, private _garageService: GarageService, private _equipmentService: EquipmentService) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      name: [null, Validators.required],
      price: [null, Validators.required],
      purpose: [null, Validators.required],
      quantity: [null, Validators.required],
      garageId: [null, Validators.required],
    })

    if (this.equipment)
      this.form.patchValue({
        ...this.equipment, garageId: this.equipment.garage.id
      })

    this._garageService.getAll().subscribe(res => {
      this.garageItems = res.data
    })
  }


  onSubmit() {
    this.submitted = true
    if (this.form.invalid) return

    let observable = this._equipmentService.create(this.form.value);
    if (this.equipment)
      observable = this._equipmentService.update(
        this.form.value,
        this.equipment.id
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
