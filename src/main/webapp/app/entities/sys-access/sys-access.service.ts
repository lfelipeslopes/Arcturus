import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysAccess } from 'app/shared/model/sys-access.model';

type EntityResponseType = HttpResponse<ISysAccess>;
type EntityArrayResponseType = HttpResponse<ISysAccess[]>;

@Injectable({ providedIn: 'root' })
export class SysAccessService {
  public resourceUrl = SERVER_API_URL + 'api/sys-accesses';

  constructor(protected http: HttpClient) {}

  create(sysAccess: ISysAccess): Observable<EntityResponseType> {
    return this.http.post<ISysAccess>(this.resourceUrl, sysAccess, { observe: 'response' });
  }

  update(sysAccess: ISysAccess): Observable<EntityResponseType> {
    return this.http.put<ISysAccess>(this.resourceUrl, sysAccess, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysAccess>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysAccess[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
