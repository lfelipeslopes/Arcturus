import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';

type EntityResponseType = HttpResponse<ISysIpGroupItem>;
type EntityArrayResponseType = HttpResponse<ISysIpGroupItem[]>;

@Injectable({ providedIn: 'root' })
export class SysIpGroupItemService {
  public resourceUrl = SERVER_API_URL + 'api/sys-ip-group-items';

  constructor(protected http: HttpClient) {}

  create(sysIpGroupItem: ISysIpGroupItem): Observable<EntityResponseType> {
    return this.http.post<ISysIpGroupItem>(this.resourceUrl, sysIpGroupItem, { observe: 'response' });
  }

  update(sysIpGroupItem: ISysIpGroupItem): Observable<EntityResponseType> {
    return this.http.put<ISysIpGroupItem>(this.resourceUrl, sysIpGroupItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysIpGroupItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysIpGroupItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
