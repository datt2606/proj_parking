import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { FormGroup, FormBuilder } from "@angular/forms";
import { Filter } from "@core/components/data-table-filter/data-table-filter.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Page } from "app/models/page";
import { Vehicle } from "app/models/vehicle";
import { ToastrService } from "ngx-toastr";
import { Subject } from "rxjs";
import { map, takeUntil } from "rxjs/operators";
import Swal from "sweetalert2";
import { VehicleService } from "./vehicle.service";
import { VehicleCategoryService } from "../vehicle-category/vehicle-category.service";
import { AuthenticationService } from "app/auth/service";

@Component({
  selector: "app-vehicle",
  templateUrl: "./vehicle.component.html",
  styleUrls: ["./vehicle.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class VehicleComponent implements OnInit {
  public filters: Filter[] = [];
  public form: FormGroup;
  private _unsubscribeAll = new Subject<void>();
  public editVehicle: Vehicle;
  public page = new Page<Vehicle>();
  public isLoading = false;

  constructor(
    private _fb: FormBuilder,
    private _vehicleService: VehicleService,
    private _modalService: NgbModal,
    private _toastrService: ToastrService,
    private _vehicleCategoryService: VehicleCategoryService,
    private _authService: AuthenticationService
  ) {}

  async ngOnInit(): Promise<void> {
    this.form = this._fb.group({
      page: 0,
      limit: 10,
      isPaginate: true,
      searchText: "",
      customerId: this._authService.currentUserValue.id,
      categoryId: null,
    });

    this.filters = [
      {
        type: "SingleSelect",
        label: "Loại phương tiện",
        controlName: "categoryId",
        column: 3,
        placeholder: "Loại phương tiện",
        items: await this._vehicleCategoryService
          .getAll()
          .pipe(map((res) => res.data))
          .toPromise(),
        bindLabel: "name",
        bindValue: "id",
        clearable: true,
      },
      {
        type: "Text",
        label: "Tên phương tiện",
        controlName: "searchText",
        column: 3,
        placeholder: "Tên phương tiện",
      },
    ];

    this.form.valueChanges
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(() => {
        setTimeout(() => {
          this.getDataTable();
        });
      });
  }

  onFilterChange(event: { [key: string]: string | number | boolean }) {
    this.form.patchValue({
      ...event,
      page: 0,
    });
  }

  getDataTable() {
    this.isLoading = true;
    this._vehicleService.search(this.form.value).subscribe((res) => {
      this.page = res.data;
      this.isLoading = false;
    });
  }

  setPage(event: {
    count: number;
    pageSize: number;
    limit: number;
    offset: number;
  }) {
    this.form.patchValue({ page: event.offset });
  }

  openForm(editVehicle: Vehicle, modal: any) {
    this._modalService.open(modal, {
      centered: true,
    });

    this.editVehicle = editVehicle;
  }

  deleteOne(row: Vehicle) {
    Swal.fire({
      title: `Xóa phương tiện ${row.vehicleName}?`,
      text: "Bạn sẽ không thể hoàn tác điều này!",
      icon: "warning",
      showCancelButton: true,
      cancelButtonText: "Hủy",
      confirmButtonText: "Đồng ý!",
      heightAuto: false,
      allowOutsideClick: () => {
        return !Swal.isLoading();
      },
    }).then((result) => {
      if (result.isConfirmed) {
        this._vehicleService.delete(row.id).subscribe(() => {
          this._toastrService.success("", "Thành công", {
            positionClass: "toast-top-center",
            toastClass: "toast ngx-toastr",
            closeButton: true,
          });
          this.getDataTable();
        });
      }
    });
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
}
