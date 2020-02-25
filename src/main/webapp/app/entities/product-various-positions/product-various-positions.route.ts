import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductVariousPositions } from 'app/shared/model/product-various-positions.model';
import { ProductVariousPositionsService } from './product-various-positions.service';
import { ProductVariousPositionsComponent } from './product-various-positions.component';
import { ProductVariousPositionsDetailComponent } from './product-various-positions-detail.component';
import { ProductVariousPositionsUpdateComponent } from './product-various-positions-update.component';
import { ProductVariousPositionsDeletePopupComponent } from './product-various-positions-delete-dialog.component';
import { IProductVariousPositions } from 'app/shared/model/product-various-positions.model';

@Injectable({ providedIn: 'root' })
export class ProductVariousPositionsResolve implements Resolve<IProductVariousPositions> {
  constructor(private service: ProductVariousPositionsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductVariousPositions> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductVariousPositions>) => response.ok),
        map((productVariousPositions: HttpResponse<ProductVariousPositions>) => productVariousPositions.body)
      );
    }
    return of(new ProductVariousPositions());
  }
}

export const productVariousPositionsRoute: Routes = [
  {
    path: '',
    component: ProductVariousPositionsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'barLevelServiceApp.productVariousPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductVariousPositionsDetailComponent,
    resolve: {
      productVariousPositions: ProductVariousPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productVariousPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductVariousPositionsUpdateComponent,
    resolve: {
      productVariousPositions: ProductVariousPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productVariousPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductVariousPositionsUpdateComponent,
    resolve: {
      productVariousPositions: ProductVariousPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productVariousPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productVariousPositionsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductVariousPositionsDeletePopupComponent,
    resolve: {
      productVariousPositions: ProductVariousPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productVariousPositions.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
