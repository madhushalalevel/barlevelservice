import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Inventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';
import { InventoryComponent } from './inventory.component';
import { InventoryDetailComponent } from './inventory-detail.component';
import { InventoryUpdateComponent } from './inventory-update.component';
import { InventoryDeletePopupComponent } from './inventory-delete-dialog.component';
import { IInventory } from 'app/shared/model/inventory.model';

@Injectable({ providedIn: 'root' })
export class InventoryResolve implements Resolve<IInventory> {
  constructor(private service: InventoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Inventory>) => response.ok),
        map((inventory: HttpResponse<Inventory>) => inventory.body)
      );
    }
    return of(new Inventory());
  }
}

export const inventoryRoute: Routes = [
  {
    path: '',
    component: InventoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'barLevelServiceApp.inventory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventoryDetailComponent,
    resolve: {
      inventory: InventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventoryUpdateComponent,
    resolve: {
      inventory: InventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventoryUpdateComponent,
    resolve: {
      inventory: InventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inventoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InventoryDeletePopupComponent,
    resolve: {
      inventory: InventoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.inventory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
