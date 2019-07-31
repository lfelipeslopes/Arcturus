import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysGroup } from 'app/shared/model/sys-group.model';

type EntityResponseType = HttpResponse<ISysGroup>;
type EntityArrayResponseType = HttpResponse<ISysGroup[]>;

@Injectable({ providedIn: 'root' })
export class SysGroupService {
  public resourceUrl = SERVER_API_URL + 'api/sys-groups';

  constructor(protected http: HttpClient) {}

  create(sysGroup: ISysGroup): Observable<EntityResponseType> {
    return this.http.post<ISysGroup>(this.resourceUrl, sysGroup, { observe: 'response' });
  }

  update(sysGroup: ISysGroup): Observable<EntityResponseType> {
    return this.http.put<ISysGroup>(this.resourceUrl, sysGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
