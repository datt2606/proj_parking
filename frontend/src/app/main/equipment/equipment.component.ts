import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Filter } from '@core/components/data-table-filter/data-table-filter.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Equipment } from 'app/models/equipment';
import { Page } from 'app/models/page';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { GarageService } from '../garage/garage.service';
import { EquipmentService } from './equipment.service';

@Component({
  selector: 'app-equipment',
  templateUrl: './equipment.component.html',
  styleUrls: ['./equipment.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class EquipmentComponent implements OnInit {
  public filters: Filter[] = [];
  public form: FormGroup
  private _unsubscribeAll = new Subject<void>();
  public editEquipment: Equipment
  public page = new Page<Equipment>();
  public isLoading = false;

  constructor(
    private _equipmentService: EquipmentService,
    private _modalService: NgbModal,
    private _toastrService: ToastrService,
    private _fb: FormBuilder,
    private _garageService: GarageService
  ) { }

  async ngOnInit() {
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
        type: "SingleSelect",
        label: "Bãi đỗ",
        controlName: "garageId",
        column: 3,
        placeholder: "Bãi đỗ",
        items: await this._garageService.getAll().pipe(map(res => res.data)).toPromise(),
        bindLabel: "location",
        bindValue: "id",
        clearable: true
      },
      {
        type: "Text",
        label: "Tên thiết bị",
        controlName: "searchText",
        column: 3,
        placeholder: "Tên thiết bị",
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
    this._equipmentService.search(this.form.value).subscribe((res) => {
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

  openForm(editEquipment: Equipment, modal: any) {
    this._modalService.open(modal, {
      centered: true,
    });

    this.editEquipment = editEquipment
  }

  deleteOne(row: Equipment) {
    Swal.fire({
      title: `Xóa thiết bị ${row.name}?`,
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
