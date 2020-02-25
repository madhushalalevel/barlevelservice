import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShelf } from 'app/shared/model/shelf.model';

type EntityResponseType = HttpResponse<IShelf>;
type EntityArrayResponseType = HttpResponse<IShelf[]>;

@Injectable({ providedIn: 'root' })
export class ShelfService {
  public resourceUrl = SERVER_API_URL + 'api/shelves';

  constructor(protected http: HttpClient) {}

  create(shelf: IShelf): Observable<EntityResponseType> {
    return this.http.post<IShelf>(this.resourceUrl, shelf, { observe: 'response' });
  }

  update(shelf: IShelf): Observable<EntityResponseType> {
    return this.http.put<IShelf>(this.resourceUrl, shelf, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShelf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShelf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
