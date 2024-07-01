import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Page } from "app/models/page";
import { Space } from "app/models/space";

@Component({
  selector: "app-garage-space-grid",
  templateUrl: "./garage-space-grid.component.html",
  styleUrls: ["./garage-space-grid.component.scss"],
})
export class GarageSpaceGridComponent implements OnInit {
  @Input("spacePage") public spacePage: Page<Space>;
  @Output("onPageChange") public onPageChange = new EventEmitter<number>();
  @Output("onSpaceChange") public onSpaceChange = new EventEmitter<Space>();
  public selected = new Space();

  constructor() {}

  ngOnInit(): void {}

  spaceChange(space: Space) {
    if (!space.isOccupied) {
      this.selected = space;
      this.onSpaceChange.emit(space);
    }
  }

  pageChange(offset) {
    this.onPageChange.emit(offset);
  }
}
