import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductPositions } from 'app/shared/model/product-positions.model';
import { ProductPositionsService } from './product-positions.service';
import { ProductPositionsComponent } from './product-positions.component';
import { ProductPositionsDetailComponent } from './product-positions-detail.component';
import { ProductPositionsUpdateComponent } from './product-positions-update.component';
import { ProductPositionsDeletePopupComponent } from './product-positions-delete-dialog.component';
import { IProductPositions } from 'app/shared/model/product-positions.model';

@Injectable({ providedIn: 'root' })
export class ProductPositionsResolve implements Resolve<IProductPositions> {
  constructor(private service: ProductPositionsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductPositions> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductPositions>) => response.ok),
        map((productPositions: HttpResponse<ProductPositions>) => productPositions.body)
      );
    }
    return of(new ProductPositions());
  }
}

export const productPositionsRoute: Routes = [
  {
    path: '',
    component: ProductPositionsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'barLevelServiceApp.productPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductPositionsDetailComponent,
    resolve: {
      productPositions: ProductPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductPositionsUpdateComponent,
    resolve: {
      productPositions: ProductPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductPositionsUpdateComponent,
    resolve: {
      productPositions: ProductPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productPositionsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductPositionsDeletePopupComponent,
    resolve: {
      productPositions: ProductPositionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productPositions.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
