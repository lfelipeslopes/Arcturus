import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysEnterprise } from 'app/shared/model/sys-enterprise.model';
import { SysEnterpriseService } from './sys-enterprise.service';
import { SysEnterpriseComponent } from './sys-enterprise.component';
import { SysEnterpriseDetailComponent } from './sys-enterprise-detail.component';
import { SysEnterpriseUpdateComponent } from './sys-enterprise-update.component';
import { SysEnterpriseDeletePopupComponent } from './sys-enterprise-delete-dialog.component';
import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';

@Injectable({ providedIn: 'root' })
export class SysEnterpriseResolve implements Resolve<ISysEnterprise> {
  constructor(private service: SysEnterpriseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysEnterprise> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysEnterprise>) => response.ok),
        map((sysEnterprise: HttpResponse<SysEnterprise>) => sysEnterprise.body)
      );
    }
    return of(new SysEnterprise());
  }
}

export const sysEnterpriseRoute: Routes = [
  {
    path: '',
    component: SysEnterpriseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysEnterprises'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysEnterpriseDetailComponent,
    resolve: {
      sysEnterprise: SysEnterpriseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysEnterprises'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysEnterpriseUpdateComponent,
    resolve: {
      sysEnterprise: SysEnterpriseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysEnterprises'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysEnterpriseUpdateComponent,
    resolve: {
      sysEnterprise: SysEnterpriseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysEnterprises'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysEnterprisePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysEnterpriseDeletePopupComponent,
    resolve: {
      sysEnterprise: SysEnterpriseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysEnterprises'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
