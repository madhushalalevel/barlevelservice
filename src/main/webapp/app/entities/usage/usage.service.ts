import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUsage } from 'app/shared/model/usage.model';

type EntityResponseType = HttpResponse<IUsage>;
type EntityArrayResponseType = HttpResponse<IUsage[]>;

@Injectable({ providedIn: 'root' })
export class UsageService {
  public resourceUrl = SERVER_API_URL + 'api/usages';

  constructor(protected http: HttpClient) {}

  create(usage: IUsage): Observable<EntityResponseType> {
    return this.http.post<IUsage>(this.resourceUrl, usage, { observe: 'response' });
  }

  update(usage: IUsage): Observable<EntityResponseType> {
    return this.http.put<IUsage>(this.resourceUrl, usage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUsage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUsage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
