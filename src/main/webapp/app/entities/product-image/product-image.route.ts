import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductImage, ProductImage } from 'app/shared/model/product-image.model';
import { ProductImageService } from './product-image.service';
import { ProductImageComponent } from './product-image.component';
import { ProductImageDetailComponent } from './product-image-detail.component';
import { ProductImageUpdateComponent } from './product-image-update.component';

@Injectable({ providedIn: 'root' })
export class ProductImageResolve implements Resolve<IProductImage> {
  constructor(private service: ProductImageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductImage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productImage: HttpResponse<ProductImage>) => {
          if (productImage.body) {
            return of(productImage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductImage());
  }
}

export const productImageRoute: Routes = [
  {
    path: '',
    component: ProductImageComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'barLevelServiceApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductImageDetailComponent,
    resolve: {
      productImage: ProductImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductImageUpdateComponent,
    resolve: {
      productImage: ProductImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductImageUpdateComponent,
    resolve: {
      productImage: ProductImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
