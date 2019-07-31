import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysPersonType } from 'app/shared/model/sys-person-type.model';

type EntityResponseType = HttpResponse<ISysPersonType>;
type EntityArrayResponseType = HttpResponse<ISysPersonType[]>;

@Injectable({ providedIn: 'root' })
export class SysPersonTypeService {
  public resourceUrl = SERVER_API_URL + 'api/sys-person-types';

  constructor(protected http: HttpClient) {}

  create(sysPersonType: ISysPersonType): Observable<EntityResponseType> {
    return this.http.post<ISysPersonType>(this.resourceUrl, sysPersonType, { observe: 'response' });
  }

  update(sysPersonType: ISysPersonType): Observable<EntityResponseType> {
    return this.http.put<ISysPersonType>(this.resourceUrl, sysPersonType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysPersonType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysPersonType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
