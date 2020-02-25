import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductImage } from 'app/shared/model/product-image.model';
import { ProductImageService } from './product-image.service';
import { ProductImageComponent } from './product-image.component';
import { ProductImageDetailComponent } from './product-image-detail.component';
import { ProductImageUpdateComponent } from './product-image-update.component';
import { ProductImageDeletePopupComponent } from './product-image-delete-dialog.component';
import { IProductImage } from 'app/shared/model/product-image.model';

@Injectable({ providedIn: 'root' })
export class ProductImageResolve implements Resolve<IProductImage> {
  constructor(private service: ProductImageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductImage> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductImage>) => response.ok),
        map((productImage: HttpResponse<ProductImage>) => productImage.body)
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

export const productImagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductImageDeletePopupComponent,
    resolve: {
      productImage: ProductImageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.productImage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
