import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryStock } from 'app/shared/model/inventory-stock.model';

type EntityResponseType = HttpResponse<IInventoryStock>;
type EntityArrayResponseType = HttpResponse<IInventoryStock[]>;

@Injectable({ providedIn: 'root' })
export class InventoryStockService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-stocks';

  constructor(protected http: HttpClient) {}

  create(inventoryStock: IInventoryStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryStock);
    return this.http
      .post<IInventoryStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryStock: IInventoryStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryStock);
    return this.http
      .put<IInventoryStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventoryStock: IInventoryStock): IInventoryStock {
    const copy: IInventoryStock = Object.assign({}, inventoryStock, {
      datetime: inventoryStock.datetime && inventoryStock.datetime.isValid() ? inventoryStock.datetime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datetime = res.body.datetime ? moment(res.body.datetime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventoryStock: IInventoryStock) => {
        inventoryStock.datetime = inventoryStock.datetime ? moment(inventoryStock.datetime) : undefined;
      });
    }
    return res;
  }
}
