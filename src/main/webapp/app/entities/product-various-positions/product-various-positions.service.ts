import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductVariousPositions } from 'app/shared/model/product-various-positions.model';

type EntityResponseType = HttpResponse<IProductVariousPositions>;
type EntityArrayResponseType = HttpResponse<IProductVariousPositions[]>;

@Injectable({ providedIn: 'root' })
export class ProductVariousPositionsService {
  public resourceUrl = SERVER_API_URL + 'api/product-various-positions';

  constructor(protected http: HttpClient) {}

  create(productVariousPositions: IProductVariousPositions): Observable<EntityResponseType> {
    return this.http.post<IProductVariousPositions>(this.resourceUrl, productVariousPositions, { observe: 'response' });
  }

  update(productVariousPositions: IProductVariousPositions): Observable<EntityResponseType> {
    return this.http.put<IProductVariousPositions>(this.resourceUrl, productVariousPositions, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductVariousPositions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductVariousPositions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
