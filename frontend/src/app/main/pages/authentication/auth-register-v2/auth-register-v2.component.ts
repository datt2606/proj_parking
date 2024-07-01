import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";

import { DatePipe } from "@angular/common";
import { Router } from "@angular/router";
import { CoreConfigService } from "@core/services/config.service";
import { AuthenticationService } from "app/auth/service";
import { FlatpickrOptions } from "ng2-flatpickr";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-auth-register-v2",
  templateUrl: "./auth-register-v2.component.html",
  styleUrls: ["./auth-register-v2.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class AuthRegisterV2Component implements OnInit {
  // Public
  public coreConfig: any;
  public passwordTextType: boolean;
  public registerForm: FormGroup;
  public submitted = false;
  public genderItems = [{
    name: "Nam",
    value: "MALE"
  }, { name: "Nữ", value: "FEMALE" }]
  public flatpickrConfig: FlatpickrOptions;

  // Private
  private _unsubscribeAll: Subject<any>;

  /**
   * Constructor
   *
   * @param {CoreConfigService} _coreConfigService
   * @param {FormBuilder} _formBuilder
   */
  constructor(
    private _coreConfigService: CoreConfigService,
    private _formBuilder: FormBuilder,
    private _authService: AuthenticationService,
    private _toastrService: ToastrService,
    private _router: Router
  ) {
    this._unsubscribeAll = new Subject();

    // Configure the layout
    this._coreConfigService.config = {
      layout: {
        navbar: {
          hidden: true,
        },
        menu: {
          hidden: true,
        },
        footer: {
          hidden: true,
        },
        customizer: false,
        enableLocalStorage: false,
      },
    };
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  /**
   * Toggle password
   */
  togglePasswordTextType() {
    this.passwordTextType = !this.passwordTextType;
  }

  /**
   * On Submit
   */
  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this._authService.register(this.registerForm.value).subscribe(res => {
      this._toastrService.success(
        "",
        "Thành công",
        { toastClass: "toast ngx-toastr", closeButton: true }
      );
      this._router.navigate(["/pages/authentication/login-v2"]);
    })

  }

  // Lifecycle Hooks
  // -----------------------------------------------------------------------------------------------------

  /**
   * On init
   */
  ngOnInit(): void {
    this.flatpickrConfig = {
      altFormat: "d-m-Y",
      altInput: true,
      onChange: (event) => {
        const pipe = new DatePipe("en-US");
        this.registerForm.patchValue({
          dateOfBirth: pipe.transform(event[0], "dd/MM/yyyy"),
        });
      },
    };

    this.registerForm = this._formBuilder.group({
      username: ["", [Validators.required]],
      email: ["", [Validators.required, Validators.email]],
      password: ["", Validators.required],
      fullName: ["", Validators.required],
      phoneNumber: ["", Validators.required],
      address: ["", Validators.required],
      gender: [null, Validators.required],
      dateOfBirth: [null, Validators.required],
      identityCardNumber: ["", Validators.required],
    });

    // Subscribe to config changes
    this._coreConfigService.config
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((config) => {
        this.coreConfig = config;
      });
  }

  /**
   * On destroy
   */
  ngOnDestroy(): void {
    // Unsubscribe from all subscriptions
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
}
