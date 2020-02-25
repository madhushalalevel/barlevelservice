import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventoryStock } from 'app/shared/model/inventory-stock.model';

type EntityResponseType = HttpResponse<IInventoryStock>;
type EntityArrayResponseType = HttpResponse<IInventoryStock[]>;

@Injectable({ providedIn: 'root' })
export class InventoryStockService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-stocks';

  constructor(protected http: HttpClient) {}

  create(inventoryStock: IInventoryStock): Observable<EntityResponseType> {
    return this.http.post<IInventoryStock>(this.resourceUrl, inventoryStock, { observe: 'response' });
  }

  update(inventoryStock: IInventoryStock): Observable<EntityResponseType> {
    return this.http.put<IInventoryStock>(this.resourceUrl, inventoryStock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInventoryStock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventoryStock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
