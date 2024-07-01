import { DatePipe } from "@angular/common";
import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewEncapsulation,
} from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { AuthenticationService } from "app/auth/service";
import { GarageService } from "app/main/garage/garage.service";
import { SpaceService } from "app/main/space/space.service";
import { VehicleCategoryService } from "app/main/vehicle-category/vehicle-category.service";
import { VehicleService } from "app/main/vehicle/vehicle.service";
import { Garage } from "app/models/garage";
import { Page } from "app/models/page";
import { Space } from "app/models/space";
import { Ticket } from "app/models/ticket";
import { Vehicle } from "app/models/vehicle";
import { VehicleCategory } from "app/models/vehicle-category";
import Stepper from "bs-stepper";
import { ToastrService } from "ngx-toastr";
import { Subject, combineLatest, forkJoin } from "rxjs";
import { debounceTime, startWith, switchMap, takeUntil } from "rxjs/operators";
import { TicketService } from "../ticket.service";
import { UserService } from "app/main/user/user.service";
import { User } from "app/models/user";

@Component({
  selector: "app-create-update",
  templateUrl: "./create-update.component.html",
  styleUrls: ["./create-update.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class CreateUpdateComponent implements OnInit, OnDestroy {
  @Input("modal") public modal: NgbActiveModal;
  @Input("ticket") public ticket: Ticket;
  @Output("success") public success = new EventEmitter<boolean>();
  public newVehicleForm: FormGroup;
  public form: FormGroup;
  public submitted = false;
  public vehicleCategoryItems: VehicleCategory[] = [];
  public vehicleItems: Vehicle[] = [];
  public garageItems: Garage[] = [];
  public userItems: User[] = [];
  public spacePage: Page<Space>;
  private _unsubscribeAll = new Subject<void>();
  public searchSpaceForm: FormGroup;
  private horizontalWizardStepper: Stepper;
  public vehicleOptionItems = [
    {
      value: 1,
      name: "Trong cơ sở dữ liệu",
    },
    {
      value: 2,
      name: "Phương tiện mới",
    },
  ];
  public vehicleOptionFormControl = this.vehicleOptionItems[0];
  public ticketTypeItems = [
    {
      value: "MONTH",
      name: "Theo tháng",
      cost: 100000,
      unit: "tháng",
      month: 1,
    },
    {
      value: "YEAR",
      name: "Theo năm",
      cost: 1000000,
      unit: "năm",
      month: 12,
    },
  ];
  public isCustomer = false;

  get ssf() {
    return this.searchSpaceForm.controls;
  }

  get nvf() {
    return this.newVehicleForm.controls;
  }

  get f() {
    return this.form.controls;
  }

  constructor(
    private _fb: FormBuilder,
    private _toastrService: ToastrService,
    private _vehicleService: VehicleService,
    private _vehicleCategoryService: VehicleCategoryService,
    private _authService: AuthenticationService,
    private _garageService: GarageService,
    private _spaceService: SpaceService,
    private _ticketService: TicketService,
    private _userService: UserService
  ) {}

  ngOnInit(): void {
    this.isCustomer = this._authService.isCustomer;

    this.horizontalWizardStepper = new Stepper(
      document.querySelector("#stepper1"),
      { linear: false, animation: true }
    );

    this.newVehicleForm = this._fb.group({
      vehicleName: [null, Validators.required],
      color: [null, Validators.required],
      licensePlate: [null, Validators.required],
      manufacturer: [null, Validators.required],
      customerId: [
        this.isCustomer ? this._authService.currentUserValue.id : null,
        Validators.required,
      ],
      categoryId: [null, Validators.required],
    });

    this.form = this._fb.group({
      customerId: [
        this.isCustomer ? this._authService.currentUserValue.id : null,
        Validators.required,
      ],
      parkingSpaceId: [null, Validators.required],
      vehicleId: null,
      vehicleRequest: {},
      price: [null, Validators.required],
      expiryDate: [null, Validators.required],
      ticketType: [this.ticketTypeItems[0].value, Validators.required],
      count: [null, Validators.required],
    });

    this.searchSpaceForm = this._fb.group({
      page: 0,
      limit: 25,
      garageId: [null, Validators.required],
    });

    if (this.ticket) this.form.patchValue(this.ticket);

    this._vehicleCategoryService.getAll().subscribe((res) => {
      this.vehicleCategoryItems = res.data;
    });

    this._garageService.getAll().subscribe((res) => {
      this.garageItems = res.data;
    });

    this._userService.getAll({ role: "CUSTOMER" }).subscribe((res) => {
      this.userItems = res.data;
    });

    this._vehicleService
      .getAll({
        customerId: this.isCustomer
          ? this._authService.currentUserValue.id
          : null,
      })
      .subscribe((res) => {
        this.vehicleItems = res.data;
      });

    this._vehicleCategoryService.getAll().subscribe((res) => {
      this.vehicleCategoryItems = res.data;
    });

    this.searchSpaceForm
      .get("garageId")
      .valueChanges.pipe(takeUntil(this._unsubscribeAll))
      .subscribe(() => {
        this.searchSpaceForm.patchValue({ page: 0 });
      });

    this.searchSpaceForm
      .get("page")
      .valueChanges.pipe(
        takeUntil(this._unsubscribeAll),
        debounceTime(0),
        switchMap(() => this._spaceService.search(this.searchSpaceForm.value))
      )
      .subscribe((res) => {
        this.spacePage = res.data;
      });

    combineLatest([
      this.form.get("count").valueChanges.pipe(startWith(0)),
      this.form
        .get("ticketType")
        .valueChanges.pipe(startWith(this.ticketTypeItems[0].value)),
    ])
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe((values) => {
        const ticketType = this.ticketTypeItems.find(
          (item) => item.value == values[1]
        );

        this.form.patchValue({
          price: values[0] * ticketType.cost,
        });

        const current = new Date();
        const expiryDate = new Date().setTime(
          current.getTime() +
            ticketType.month * values[0] * 30 * 24 * 60 * 60 * 1000
        );

        const pipe = new DatePipe("en-US");
        this.form.patchValue({
          expiryDate: pipe.transform(expiryDate, "dd/MM/yyyy"),
        });
      });

    this.form
      .get("customerId")
      .valueChanges.pipe(
        takeUntil(this._unsubscribeAll),
        switchMap((customerId) => {
          this.newVehicleForm.patchValue({ customerId });
          return this._vehicleService.getAll({
            customerId,
          });
        })
      )
      .subscribe((res) => {
        this.vehicleItems = res.data;
      });
  }

  onSubmit() {
    this.submitted = true;
    if (this.form.invalid) return;
    this.form.patchValue({
      vehicleRequest: this.newVehicleForm.value,
    });
    if (this.vehicleOptionFormControl.value == 2) {
      this.form.patchValue({
        vehicleId: null,
      });
    }

    this.submitted = true;
    if (this.form.invalid) return;
    let observable = this._ticketService.create(this.form.value);
    if (this.ticket)
      observable = this._ticketService.update(this.form.value, this.ticket.id);
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

  pageSpaceChange(event) {
    this.searchSpaceForm.patchValue({
      page: this.searchSpaceForm.get("page").value + event,
    });
  }

  spaceChange(event: Space) {
    this.form.patchValue({ parkingSpaceId: event.id });
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
}
