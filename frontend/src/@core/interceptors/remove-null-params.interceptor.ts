import { Injectable } from "@angular/core";
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable()
export class RemoveNullParamsInterceptor implements HttpInterceptor {
  constructor() { }

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    let params = request.params;
    for (const key of params.keys()) if (params.get(key) == null) params = params.delete(key);

    request = request.clone({ params });
    return next.handle(request);
  }
}
