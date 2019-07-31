import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';

type EntityResponseType = HttpResponse<ISysIpGroup>;
type EntityArrayResponseType = HttpResponse<ISysIpGroup[]>;

@Injectable({ providedIn: 'root' })
export class SysIpGroupService {
  public resourceUrl = SERVER_API_URL + 'api/sys-ip-groups';

  constructor(protected http: HttpClient) {}

  create(sysIpGroup: ISysIpGroup): Observable<EntityResponseType> {
    return this.http.post<ISysIpGroup>(this.resourceUrl, sysIpGroup, { observe: 'response' });
  }

  update(sysIpGroup: ISysIpGroup): Observable<EntityResponseType> {
    return this.http.put<ISysIpGroup>(this.resourceUrl, sysIpGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysIpGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysIpGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
