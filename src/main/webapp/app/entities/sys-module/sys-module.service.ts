import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysModule } from 'app/shared/model/sys-module.model';

type EntityResponseType = HttpResponse<ISysModule>;
type EntityArrayResponseType = HttpResponse<ISysModule[]>;

@Injectable({ providedIn: 'root' })
export class SysModuleService {
  public resourceUrl = SERVER_API_URL + 'api/sys-modules';

  constructor(protected http: HttpClient) {}

  create(sysModule: ISysModule): Observable<EntityResponseType> {
    return this.http.post<ISysModule>(this.resourceUrl, sysModule, { observe: 'response' });
  }

  update(sysModule: ISysModule): Observable<EntityResponseType> {
    return this.http.put<ISysModule>(this.resourceUrl, sysModule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysModule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysModule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
