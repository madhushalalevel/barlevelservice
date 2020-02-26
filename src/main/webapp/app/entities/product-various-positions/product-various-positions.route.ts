import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductVariousPositions, ProductVariousPositions } from 'app/shared/model/product-various-positions.model';
import { ProductVariousPositionsService } from './product-various-positions.service';
import { ProductVariousPositionsComponent } from './product-various-positions.component';
import { ProductVariousPositionsDetailComponent } from './product-various-positions-detail.component';
import { ProductVariousPositionsUpdateComponent } from './product-various-positions-update.component';

@Injectable({ providedIn: 'root' })
export class ProductVariousPositionsResolve implements Resolve<IProductVariousPositions> {
  constructor(private service: ProductVariousPositionsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductVariousPositions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productVariousPositions: HttpResponse<ProductVariousPositions>) => {
          if (productVariousPositions.body) {
            return of(productVariousPositions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
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
      pageTitle: 'barlevelserviceApp.productVariousPositions.home.title'
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
      pageTitle: 'barlevelserviceApp.productVariousPositions.home.title'
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
      pageTitle: 'barlevelserviceApp.productVariousPositions.home.title'
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
      pageTitle: 'barlevelserviceApp.productVariousPositions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
