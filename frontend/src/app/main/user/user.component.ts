import { Component, OnDestroy, OnInit, ViewEncapsulation } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Filter } from "@core/components/data-table-filter/data-table-filter.component";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { Page } from "app/models/page";
import { User } from "app/models/user";
import { ToastrService } from "ngx-toastr";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import Swal from "sweetalert2";
import { UserService } from "./user.service";

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class UserComponent implements OnInit, OnDestroy {
  public filters: Filter[] = [];
  public form: FormGroup;
  private _unsubscribeAll = new Subject<void>();
  public editUser: User;
  public page = new Page<User>();
  public isLoading = false;

  constructor(
    private _fb: FormBuilder,
    private _userService: UserService,
    private _modalService: NgbModal,
    private _toastrService: ToastrService
  ) {}

  ngOnInit(): void {
    this.form = this._fb.group({
      page: 0,
      limit: 10,
      isPaginate: true,
      searchText: "",
      role: "",
    });

    this.filters = [
      {
        type: "SingleSelect",
        label: "Vai trò",
        controlName: "role",
        column: 3,
        items: ["EMPLOYEE", "CUSTOMER"],
        firstValue: "EMPLOYEE",
        clearable: false,
      },
      {
        type: "Text",
        label: "Tên người dùng",
        controlName: "searchText",
        column: 3,
        placeholder: "Tên người dùng",
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
    this._userService.search(this.form.value).subscribe((res) => {
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

  openForm(editUser: User, modal: any) {
    this._modalService.open(modal, {
      centered: true,
    });

    this.editUser = editUser;
  }

  deleteOne(row: User) {
    Swal.fire({
      title: `Xóa người dùng ${row.fullName}?`,
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
        this._userService.delete(row.userId).subscribe(() => {
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
