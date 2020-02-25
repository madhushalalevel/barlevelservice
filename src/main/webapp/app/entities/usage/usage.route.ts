import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsage, Usage } from 'app/shared/model/usage.model';
import { UsageService } from './usage.service';
import { UsageComponent } from './usage.component';
import { UsageDetailComponent } from './usage-detail.component';
import { UsageUpdateComponent } from './usage-update.component';

@Injectable({ providedIn: 'root' })
export class UsageResolve implements Resolve<IUsage> {
  constructor(private service: UsageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usage: HttpResponse<Usage>) => {
          if (usage.body) {
            return of(usage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
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
