import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FlatpickrOptions } from 'ng2-flatpickr';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../user.service';
import { User } from 'app/models/user';

@Component({
  selector: 'app-create-update',
  templateUrl: './create-update.component.html',
  styleUrls: ['./create-update.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CreateUpdateComponent implements OnInit {
  @Input("modal") public modal: NgbActiveModal;
  @Input("user") public user: User;
  @Output("success") public success = new EventEmitter<boolean>();
  public form: FormGroup
  public submitted = false
  public genderItems = [{
    name: "Nam",
    value: "MALE"
  }, { name: "Nữ", value: "FEMALE" }]
  public passwordTextType: boolean;
  public flatpickrConfig: FlatpickrOptions;

  get f() {
    return this.form.controls
  }

  constructor(private _fb: FormBuilder, private _toastrService: ToastrService, private _userService: UserService) { }

  ngOnInit(): void {
    this.flatpickrConfig = {
      altFormat: "d-m-Y",
      altInput: true,
      maxDate: new Date(),
      onChange: (event) => {
        const pipe = new DatePipe("en-US");
        this.form.patchValue({
          dateOfBirth: pipe.transform(event[0], "dd/MM/yyyy"),
        });
      },
    };

    this.form = this._fb.group({
      id: "",
      username: [null, Validators.required],
      password: [null, Validators.required],
      fullName: [null, Validators.required],
      gender: [null, Validators.required],
      dateOfBirth: [null, Validators.required],
      position: [null, Validators.required],
    })

    if (this.user) {
      this.form.removeControl("password")
      this.form.removeControl("username")
      const dateSplit = this.user.dateOfBirth.split("-")
      const dobString = [dateSplit[2], dateSplit[1], dateSplit[0]].join("/")

      this.form.patchValue(({ ...this.user, dateOfBirth: dobString }))
      this.flatpickrConfig.defaultDate = new Date(this.user.dateOfBirth)
    }
  }

  togglePasswordTextType() {
    this.passwordTextType = !this.passwordTextType;
  }

  onSubmit() {
    this.submitted = true
    if (this.form.invalid) return


    let observable = this._userService.createEmployee(this.form.value);
    if (this.user)
      observable = this._userService.updateEmployee(
        this.form.value
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
