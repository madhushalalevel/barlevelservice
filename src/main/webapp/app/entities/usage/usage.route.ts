import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Usage } from 'app/shared/model/usage.model';
import { UsageService } from './usage.service';
import { UsageComponent } from './usage.component';
import { UsageDetailComponent } from './usage-detail.component';
import { UsageUpdateComponent } from './usage-update.component';
import { UsageDeletePopupComponent } from './usage-delete-dialog.component';
import { IUsage } from 'app/shared/model/usage.model';

@Injectable({ providedIn: 'root' })
export class UsageResolve implements Resolve<IUsage> {
  constructor(private service: UsageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUsage> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Usage>) => response.ok),
        map((usage: HttpResponse<Usage>) => usage.body)
      );
    }
    return of(new Usage());
  }
}

export const usageRoute: Routes = [
  {
    path: '',
    component: UsageComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'barLevelServiceApp.usage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UsageDetailComponent,
    resolve: {
      usage: UsageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.usage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UsageUpdateComponent,
    resolve: {
      usage: UsageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.usage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UsageUpdateComponent,
    resolve: {
      usage: UsageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.usage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const usagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UsageDeletePopupComponent,
    resolve: {
      usage: UsageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'barLevelServiceApp.usage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
