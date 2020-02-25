import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryStock, InventoryStock } from 'app/shared/model/inventory-stock.model';
import { InventoryStockService } from './inventory-stock.service';
import { InventoryStockComponent } from './inventory-stock.component';
import { InventoryStockDetailComponent } from './inventory-stock-detail.component';
import { InventoryStockUpdateComponent } from './inventory-stock-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryStockResolve implements Resolve<IInventoryStock> {
  constructor(private service: InventoryStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryStock: HttpResponse<InventoryStock>) => {
          if (inventoryStock.body) {
            return of(inventoryStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryStock());
  }
}

export const inventoryStockRoute: Routes = [
  {
    path: '',
    component: InventoryStockComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'barLevelServiceApp.inventoryStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventoryStockDetailComponent,
    resolve: {
      inventoryStock: InventoryStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventoryStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventoryStockUpdateComponent,
    resolve: {
      inventoryStock: InventoryStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventoryStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventoryStockUpdateComponent,
    resolve: {
      inventoryStock: InventoryStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventoryStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
