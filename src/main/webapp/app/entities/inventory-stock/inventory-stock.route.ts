import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InventoryStock } from 'app/shared/model/inventory-stock.model';
import { InventoryStockService } from './inventory-stock.service';
import { InventoryStockComponent } from './inventory-stock.component';
import { InventoryStockDetailComponent } from './inventory-stock-detail.component';
import { InventoryStockUpdateComponent } from './inventory-stock-update.component';
import { InventoryStockDeletePopupComponent } from './inventory-stock-delete-dialog.component';
import { IInventoryStock } from 'app/shared/model/inventory-stock.model';

@Injectable({ providedIn: 'root' })
export class InventoryStockResolve implements Resolve<IInventoryStock> {
  constructor(private service: InventoryStockService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventoryStock> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InventoryStock>) => response.ok),
        map((inventoryStock: HttpResponse<InventoryStock>) => inventoryStock.body)
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

export const inventoryStockPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InventoryStockDeletePopupComponent,
    resolve: {
      inventoryStock: InventoryStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventoryStock.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
