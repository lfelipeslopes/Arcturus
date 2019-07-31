import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';

type EntityResponseType = HttpResponse<ISysEnterprise>;
type EntityArrayResponseType = HttpResponse<ISysEnterprise[]>;

@Injectable({ providedIn: 'root' })
export class SysEnterpriseService {
  public resourceUrl = SERVER_API_URL + 'api/sys-enterprises';

  constructor(protected http: HttpClient) {}

  create(sysEnterprise: ISysEnterprise): Observable<EntityResponseType> {
    return this.http.post<ISysEnterprise>(this.resourceUrl, sysEnterprise, { observe: 'response' });
  }

  update(sysEnterprise: ISysEnterprise): Observable<EntityResponseType> {
    return this.http.put<ISysEnterprise>(this.resourceUrl, sysEnterprise, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysEnterprise>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysEnterprise[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
