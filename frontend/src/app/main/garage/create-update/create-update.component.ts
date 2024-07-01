import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Garage } from 'app/models/garage';
import { GarageService } from '../garage.service';
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
  @Input("garage") public garage: Garage;
  @Output("success") public success = new EventEmitter<boolean>();
  public form: FormGroup
  public submitted = false

  get f() {
    return this.form.controls
  }

  constructor(private _fb: FormBuilder, private _toastrService: ToastrService, private _garageService: GarageService) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      location: [null, Validators.required],
      capacity: [null, Validators.required],
    })

    if (this.garage)
      this.form.patchValue(this.garage)
  }


  onSubmit() {
    this.submitted = true
    if (this.form.invalid) return

    let observable = this._garageService.create(this.form.value);
    if (this.garage)
      observable = this._garageService.update(
        this.form.value,
        this.garage.id
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
