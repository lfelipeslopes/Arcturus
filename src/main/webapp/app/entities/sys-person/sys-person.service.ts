import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISysPerson } from 'app/shared/model/sys-person.model';

type EntityResponseType = HttpResponse<ISysPerson>;
type EntityArrayResponseType = HttpResponse<ISysPerson[]>;

@Injectable({ providedIn: 'root' })
export class SysPersonService {
  public resourceUrl = SERVER_API_URL + 'api/sys-people';

  constructor(protected http: HttpClient) {}

  create(sysPerson: ISysPerson): Observable<EntityResponseType> {
    return this.http.post<ISysPerson>(this.resourceUrl, sysPerson, { observe: 'response' });
  }

  update(sysPerson: ISysPerson): Observable<EntityResponseType> {
    return this.http.put<ISysPerson>(this.resourceUrl, sysPerson, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISysPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISysPerson[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
