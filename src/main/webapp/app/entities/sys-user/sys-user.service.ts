import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysUser } from 'app/shared/model/sys-user.model';

type EntityResponseType = HttpResponse<ISysUser>;
type EntityArrayResponseType = HttpResponse<ISysUser[]>;

@Injectable({ providedIn: 'root' })
export class SysUserService {
  public resourceUrl = SERVER_API_URL + 'api/sys-users';

  constructor(protected http: HttpClient) {}

  create(sysUser: ISysUser): Observable<EntityResponseType> {
    return this.http.post<ISysUser>(this.resourceUrl, sysUser, { observe: 'response' });
  }

  update(sysUser: ISysUser): Observable<EntityResponseType> {
    return this.http.put<ISysUser>(this.resourceUrl, sysUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
