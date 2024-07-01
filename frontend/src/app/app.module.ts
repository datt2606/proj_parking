import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RouterModule, Routes } from "@angular/router";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { TranslateModule } from "@ngx-translate/core";
import "hammerjs";
import { ToastrModule } from "ngx-toastr"; // For auth after login toast

import { CoreCommonModule } from "@core/common.module";
import { CoreSidebarModule, CoreThemeCustomizerModule } from "@core/components";
import { CoreModule } from "@core/core.module";

import { coreConfig } from "app/app-config";

import { AppComponent } from "app/app.component";
import { LayoutModule } from "app/layout/layout.module";
import { AuthGuard, ErrorInterceptor, JwtInterceptor } from "./auth/helpers";
import { Role } from "./auth/models";
import { RemoveNullParamsInterceptor } from "@core/interceptors/remove-null-params.interceptor";

const appRoutes: Routes = [
  {
    path: "pages",
    loadChildren: () =>
      import("./main/pages/pages.module").then((m) => m.PagesModule),
  },
  {
    path: "user",
    loadChildren: () =>
      import("./main/user/user.module").then((m) => m.UserModule),
    canActivate: [AuthGuard],
    data: { roles: [Role.Manager] },
  },
  {
    path: "vehicle-category",
    loadChildren: () =>
      import("./main/vehicle-category/vehicle-category.module").then(
        (m) => m.VehicleCategoryModule
      ),
    canActivate: [AuthGuard],
    data: { roles: [Role.Manager, Role.Employee] },
  },
  {
    path: "vehicle",
    loadChildren: () =>
      import("./main/vehicle/vehicle.module").then((m) => m.VehicleModule),
    canActivate: [AuthGuard],
    data: { roles: [Role.Customer] },
  },
  {
    path: "garage",
    loadChildren: () =>
      import("./main/garage/garage.module").then((m) => m.GarageModule),
    canActivate: [AuthGuard],
    data: { roles: [Role.Manager, Role.Employee] },
  },
  {
    path: "equipment",
    loadChildren: () =>
      import("./main/equipment/equipment.module").then(
        (m) => m.EquipmentModule
      ),
    canActivate: [AuthGuard],
    data: { roles: [Role.Manager, Role.Employee] },
  },
  {
    path: "ticket",
    loadChildren: () =>
      import("./main/ticket/ticket.module").then((m) => m.TicketModule),
    canActivate: [AuthGuard],
    // data: { roles: [Role.Employee] },
  },
  {
    path: "dashboard",
    loadChildren: () =>
      import("./main/dashboard/dashboard.module").then(
        (m) => m.DashboardModule
      ),
    canActivate: [AuthGuard],
    data: { roles: [Role.Manager] },
  },
  {
    path: "",
    redirectTo: "/ticket",
    pathMatch: "full",
  },
  {
    path: "**",
    redirectTo: "/pages/miscellaneous/error", //Error 404 - Page not found
  },
];

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {
      scrollPositionRestoration: "enabled", // Add options right here
      relativeLinkResolution: "legacy",
    }),
    TranslateModule.forRoot(),

    //NgBootstrap
    NgbModule,
    ToastrModule.forRoot(),

    // Core modules
    CoreModule.forRoot(coreConfig),
    CoreCommonModule,
    CoreSidebarModule,
    CoreThemeCustomizerModule,

    // App modules
    LayoutModule,
  ],

  bootstrap: [AppComponent],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RemoveNullParamsInterceptor,
      multi: true,
    },
  ],
})
export class AppModule {}
