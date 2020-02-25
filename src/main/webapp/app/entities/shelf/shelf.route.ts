import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Shelf } from 'app/shared/model/shelf.model';
import { ShelfService } from './shelf.service';
import { ShelfComponent } from './shelf.component';
import { ShelfDetailComponent } from './shelf-detail.component';
import { ShelfUpdateComponent } from './shelf-update.component';
import { ShelfDeletePopupComponent } from './shelf-delete-dialog.component';
import { IShelf } from 'app/shared/model/shelf.model';

@Injectable({ providedIn: 'root' })
export class ShelfResolve implements Resolve<IShelf> {
  constructor(private service: ShelfService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShelf> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Shelf>) => response.ok),
        map((shelf: HttpResponse<Shelf>) => shelf.body)
      );
    }
    return of(new Shelf());
  }
}

export const shelfRoute: Routes = [
  {
    path: '',
    component: ShelfComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'barLevelServiceApp.shelf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShelfDetailComponent,
    resolve: {
      shelf: ShelfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.shelf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShelfUpdateComponent,
    resolve: {
      shelf: ShelfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.shelf.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShelfUpdateComponent,
    resolve: {
      shelf: ShelfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.shelf.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shelfPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ShelfDeletePopupComponent,
    resolve: {
      shelf: ShelfResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.shelf.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
