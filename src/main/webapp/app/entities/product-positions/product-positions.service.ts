import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductPositions } from 'app/shared/model/product-positions.model';

type EntityResponseType = HttpResponse<IProductPositions>;
type EntityArrayResponseType = HttpResponse<IProductPositions[]>;

@Injectable({ providedIn: 'root' })
export class ProductPositionsService {
  public resourceUrl = SERVER_API_URL + 'api/product-positions';

  constructor(protected http: HttpClient) {}

  create(productPositions: IProductPositions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productPositions);
    return this.http
      .post<IProductPositions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(productPositions: IProductPositions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productPositions);
    return this.http
      .put<IProductPositions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProductPositions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProductPositions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(productPositions: IProductPositions): IProductPositions {
    const copy: IProductPositions = Object.assign({}, productPositions, {
      updatedTime:
        productPositions.updatedTime && productPositions.updatedTime.isValid() ? productPositions.updatedTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.updatedTime = res.body.updatedTime ? moment(res.body.updatedTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((productPositions: IProductPositions) => {
        productPositions.updatedTime = productPositions.updatedTime ? moment(productPositions.updatedTime) : undefined;
      });
    }
    return res;
  }
}
