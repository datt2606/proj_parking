import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Garage } from 'app/models/garage';
import { GarageService } from './garage.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Filter } from '@core/components/data-table-filter/data-table-filter.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Page } from 'app/models/page';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-garage',
  templateUrl: './garage.component.html',
  styleUrls: ['./garage.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class GarageComponent implements OnInit {
  public filters: Filter[] = [];
  public form: FormGroup
  private _unsubscribeAll = new Subject<void>();
  public editGarage: Garage
  public page = new Page<Garage>();
  public isLoading = false;

  constructor(
    private _garageService: GarageService,
    private _modalService: NgbModal,
    private _toastrService: ToastrService,
    private _fb: FormBuilder
  ) { }

  ngOnInit() {
    this.form = this._fb.group({
      page: 0,
      limit: 10,
      isPaginate: true,
      searchText: "",
      customerId: null,
      categoryId: null
    });

    this.filters = [
      {
        type: "Text",
        label: "Địa chỉ",
        controlName: "searchText",
        column: 3,
        placeholder: "Địa chỉ",
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
    this._garageService.search(this.form.value).subscribe((res) => {
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

  openForm(editGarage: Garage, modal: any) {
    this._modalService.open(modal, {
      centered: true,
    });

    this.editGarage = editGarage
  }

  deleteOne(row: Garage) {
    Swal.fire({
      title: `Xóa bãi đỗ ${row.location}?`,
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
        this._garageService.delete(row.id).subscribe(() => {
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
