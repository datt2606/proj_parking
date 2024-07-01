import { CoreMenu } from "@core/types";
import { Role } from "app/auth/models";

export const menu: CoreMenu[] = [
  {
    id: "ticket",
    title: "Quản lý vé",
    type: "item",
    url: "ticket",
  },
  {
    id: "user",
    title: "Người dùng",
    type: "item",
    url: "user",
    role: [Role.Manager],
  },
  {
    id: "vehicle-category",
    title: "Loại phương tiện",
    type: "item",
    url: "vehicle-category",
    role: [Role.Manager, Role.Employee],
  },
  {
    id: "vehicle",
    title: "Phương tiện",
    type: "item",
    url: "vehicle",
    role: [Role.Customer],
  },
  {
    id: "garage",
    title: "Quản lý bãi đỗ",
    type: "item",
    url: "garage",
    role: [Role.Manager, Role.Employee],
  },
  {
    id: "equipment",
    title: "Thiết bị",
    type: "item",
    url: "equipment",
    role: [Role.Manager, Role.Employee],
  },
  {
    id: "dashboard",
    title: "Thống kê",
    type: "item",
    url: "dashboard",
    role: [Role.Manager],
  },
];
