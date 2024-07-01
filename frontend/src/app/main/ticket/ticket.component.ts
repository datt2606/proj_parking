import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { SpaceService } from "../space/space.service";
import { Space } from "app/models/space";
import { Ticket } from "app/models/ticket";
import { UserService } from "../user/user.service";
import { TicketService } from "./ticket.service";
import { FormGroup, FormBuilder } from "@angular/forms";
import { Filter } from "@core/components/data-table-filter/data-table-filter.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Page } from "app/models/page";
import { ToastrService } from "ngx-toastr";
import { Subject } from "rxjs";
import { map, takeUntil } from "rxjs/operators";
import Swal from "sweetalert2";
import { VehicleCategoryService } from "../vehicle-category/vehicle-category.service";
import { AuthenticationService } from "app/auth/service";

@Component({
  selector: "app-ticket",
  templateUrl: "./ticket.component.html",
  styleUrls: ["./ticket.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class TicketComponent implements OnInit {
  public filters: Filter[] = [];
  public form: FormGroup;
  private _unsubscribeAll = new Subject<void>();
  public editTicket: Ticket;
  public page = new Page<Ticket>();
  public isLoading = false;
  public isCustomer = false;

  constructor(
    private _fb: FormBuilder,
    private _modalService: NgbModal,
    private _toastrService: ToastrService,
    private _vehicleCategoryService: VehicleCategoryService,
    private _userService: UserService,
    private _ticketService: TicketService,
    private _authService: AuthenticationService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isCustomer = this._authService.isCustomer;
    this.form = this._fb.group({
      page: 0,
      limit: 10,
      isPaginate: true,
      searchText: "",
      customerId: this.isCustomer
        ? this._authService.currentUserValue.id
        : null,
      categoryId: null,
      isActive: this.isCustomer ? true : null,
      ticketType: null,
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
        type: "SingleSelect",
        label: "Loại vé",
        controlName: "ticketType",
        column: 3,
        placeholder: "Loại vé",
        items: [
          {
            value: "MONTH",
            name: "Theo tháng",
          },
          {
            value: "YEAR",
            name: "Theo năm",
          },
        ],
        bindLabel: "name",
        bindValue: "value",
        clearable: true,
      },
      {
        type: "Text",
        label: "Tìm kiếm",
        controlName: "searchText",
        column: 3,
        placeholder: "Tìm kiếm",
      },
    ];

    if (!this.isCustomer)
      this.filters = [
        {
          type: "SingleSelect",
          label: "Khách hàng",
          controlName: "customerId",
          column: 3,
          placeholder: "Khách hàng",
          items: await this._userService
            .getAll()
            .pipe(map((res) => res.data))
            .toPromise(),
          bindLabel: "fullName",
          bindValue: "id",
          clearable: true,
        },
        ...this.filters,
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
    this._ticketService.search(this.form.value).subscribe((res) => {
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

  openForm(editTicket: Ticket, modal: any) {
    this._modalService.open(modal, {
      centered: true,
      size: "lg",
    });

    this.editTicket = editTicket;
  }

  deleteOne(row: Ticket) {
    Swal.fire({
      title: `Xóa vé ${row.id}?`,
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
        this._ticketService.delete(row.id).subscribe(() => {
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

  active(row: Ticket) {
    this._ticketService.active(row.id).subscribe(() => {
      this._toastrService.success("", "Thành công", {
        positionClass: "toast-top-center",
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
      this.getDataTable();
    });
  }

  parking(row: Ticket) {
    this._ticketService.parking(row.id).subscribe(() => {
      this._toastrService.success("", "Thành công", {
        positionClass: "toast-top-center",
        toastClass: "toast ngx-toastr",
        closeButton: true,
      });
      this.getDataTable();
    });
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
}
