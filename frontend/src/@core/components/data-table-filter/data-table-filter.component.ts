import { DatePipe } from "@angular/common";
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
  ViewEncapsulation,
} from "@angular/core";
import { FormControl, FormGroup } from "@angular/forms";
import { Options } from "flatpickr/dist/types/options";
import { Observable, Subject, combineLatest } from "rxjs";
import {
  debounceTime,
  distinctUntilChanged,
  startWith,
  takeUntil,
} from "rxjs/operators";

export class Filter {
  public type:
    | "Text"
    | "SingleSelect"
    | "MultiSelect"
    | "LimitSelect"
    | "DateRange";
  public label?: string;
  public placeholder?: string;
  public controlName?: string;
  public column: number;
  public firstValue?: any;

  //For Select
  public items?: any[];

  //For SingleSelect, MultiSelect
  public bindLabel?: string;
  public bindValue?: string | number;

  //For DateRange
  public startControl?: string;
  public endControl?: string;
  public startDate?: Date;
  public endDate?: Date;
  public dateRangeConfig?: Options;
  public clearable?: boolean;
  public dateFormat?: string;
}

@Component({
  selector: "app-data-table-filter",
  templateUrl: "./data-table-filter.component.html",
  styleUrls: ["./data-table-filter.component.scss"],
  encapsulation: ViewEncapsulation.None,
})
export class DataTableFilterComponent implements OnInit, OnChanges, OnDestroy {
  @Input("filters") public filters: Filter[];
  @Output("onChange") public onChange = new EventEmitter<{
    [key: string]: string | number | boolean;
  }>();
  public limitOption = [1, 10, 15, 20, 50, 100];
  public form = new FormGroup({});
  private _unsubscribeAll = new Subject<void>();

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    let observables: Observable<any>[] = [];

    for (let filter of this.filters) {
      const formControl = new FormControl();

      switch (filter.type) {
        case "Text":
          formControl.setValue("");
          observables.push(
            formControl.valueChanges.pipe(
              debounceTime(500),
              distinctUntilChanged(),
              startWith("")
            )
          );
          break;
        case "LimitSelect":
          formControl.setValue(this.limitOption[1]);
          observables.push(formControl.valueChanges.pipe(startWith("")));
          break;
        case "SingleSelect":
          formControl.setValue(null);
          observables.push(formControl.valueChanges.pipe(startWith("")));
          break;
        case "MultiSelect":
          formControl.setValue([]);
          observables.push(formControl.valueChanges.pipe(startWith("")));
          break;
        case "DateRange":
          const startControl = new FormControl();
          startControl.setValue(
            this.formatDate(filter.startDate, filter.dateFormat)
          );
          this.form.addControl(filter.startControl, startControl);
          const endControl = new FormControl();
          endControl.setValue(
            this.formatDate(filter.endDate, filter.dateFormat)
          );
          this.form.addControl(filter.endControl, endControl);
          observables.push(endControl.valueChanges);

          filter.dateRangeConfig = {
            altInput: true,
            mode: "range",
            locale: require("flatpickr/dist/l10n/vn").default.vn,
            altFormat: "d/m/Y",
            defaultDate: [filter.startDate, filter.endDate],
            onChange: (val: Date[]) => {
              if (val.length == 0) {
                startControl.patchValue(null);
                endControl.patchValue(null);
              } else if (val.length == 2) {
                const fromDate = new Date(val[0]);
                const toDate = new Date(val[1]);
                startControl.patchValue(
                  this.formatDate(fromDate, filter.dateFormat)
                );
                endControl.patchValue(
                  this.formatDate(toDate, filter.dateFormat)
                );
              }
            },
          };

          break;
        default:
          formControl.setValue(null);
          observables.push(formControl.valueChanges.pipe(startWith("")));
          break;
      }

      if (filter.firstValue) formControl.setValue(filter.firstValue);

      if (filter.controlName)
        this.form.addControl(filter.controlName, formControl);
    }

    combineLatest(observables)
      .pipe(takeUntil(this._unsubscribeAll))
      .subscribe(() => {
        setTimeout(() => {
          this.onChange.emit(this.form.value);
        }, 0);
      });
  }

  ngOnInit() { }

  formatDate(date: Date, format: string) {
    if (date) {
      const pipe = new DatePipe("en-US");
      return pipe.transform(date, format);
    }
    return null;
  }

  dateRangeClear(filter: Filter) {
    filter.dateRangeConfig.defaultDate = [];
  }

  showClearable(filter: Filter) {
    if (
      this.form.get(filter.startControl).value &&
      this.form.get(filter.endControl).value
    )
      return true;
    return false;
  }

  ngOnDestroy(): void {
    this._unsubscribeAll.next();
    this._unsubscribeAll.complete();
  }
}
