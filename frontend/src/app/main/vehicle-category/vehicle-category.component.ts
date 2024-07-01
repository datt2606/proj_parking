import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Filter } from '@core/components/data-table-filter/data-table-filter.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Page } from 'app/models/page';
import { VehicleCategory } from 'app/models/vehicle-category';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { VehicleCategoryService } from './vehicle-category.service';

@Component({
  selector: 'app-vehicle-category',
  templateUrl: './vehicle-category.component.html',
  styleUrls: ['./vehicle-category.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class VehicleCategoryComponent implements OnInit {
  public filters: Filter[] = [];
  public form: FormGroup
  private _unsubscribeAll = new Subject<void>();
  public editVehicleCategory: VehicleCategory
  public page = new Page<VehicleCategory>();
  public isLoading = false;

  constructor(
    private _fb: FormBuilder,
    private _vehicleCategoryService: VehicleCategoryService,
    private _modalService: NgbModal,
    private _toastrService: ToastrService,
  ) { }

  ngOnInit(): void {
    this.form = this._fb.group({
      page: 0,
      limit: 10,
      isPaginate: true,
      searchText: "",
    });

    this.filters = [
      {
        type: "Text",
        label: "Loại phương tiện",
        controlName: "searchText",
        column: 3,
        placeholder: "Loại phương tiện",
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
    this._vehicleCategoryService.search(this.form.value).subscribe((res) => {
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

  openForm(editVehicleCategory: VehicleCategory, modal: any) {
    this._modalService.open(modal, {
      centered: true
    });

    this.editVehicleCategory = editVehicleCategory
  }

  deleteOne(row: VehicleCategory) {
    Swal.fire({
      title: `Xóa loại phương tiện ${row.name}?`,
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
        this._vehicleCategoryService.delete(row.id).subscribe(() => {
          this._toastrService.success(
            "",
            "Thành công",
            {
              positionClass: "toast-top-center",
              toastClass: "toast ngx-toastr",
              closeButton: true,
            }
          );
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
