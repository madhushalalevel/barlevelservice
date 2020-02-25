import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductPositions, ProductPositions } from 'app/shared/model/product-positions.model';
import { ProductPositionsService } from './product-positions.service';
import { ProductPositionsComponent } from './product-positions.component';
import { ProductPositionsDetailComponent } from './product-positions-detail.component';
import { ProductPositionsUpdateComponent } from './product-positions-update.component';

@Injectable({ providedIn: 'root' })
export class ProductPositionsResolve implements Resolve<IProductPositions> {
  constructor(private service: ProductPositionsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductPositions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productPositions: HttpResponse<ProductPositions>) => {
          if (productPositions.body) {
            return of(productPositions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
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
