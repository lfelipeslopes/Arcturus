import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysLicense } from 'app/shared/model/sys-license.model';

type EntityResponseType = HttpResponse<ISysLicense>;
type EntityArrayResponseType = HttpResponse<ISysLicense[]>;

@Injectable({ providedIn: 'root' })
export class SysLicenseService {
  public resourceUrl = SERVER_API_URL + 'api/sys-licenses';

  constructor(protected http: HttpClient) {}

  create(sysLicense: ISysLicense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysLicense);
    return this.http
      .post<ISysLicense>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sysLicense: ISysLicense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sysLicense);
    return this.http
      .put<ISysLicense>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISysLicense>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISysLicense[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(sysLicense: ISysLicense): ISysLicense {
    const copy: ISysLicense = Object.assign({}, sysLicense, {
      startDate: sysLicense.startDate != null && sysLicense.startDate.isValid() ? sysLicense.startDate.toJSON() : null,
      endDate: sysLicense.endDate != null && sysLicense.endDate.isValid() ? sysLicense.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sysLicense: ISysLicense) => {
        sysLicense.startDate = sysLicense.startDate != null ? moment(sysLicense.startDate) : null;
        sysLicense.endDate = sysLicense.endDate != null ? moment(sysLicense.endDate) : null;
      });
    }
    return res;
  }
}
