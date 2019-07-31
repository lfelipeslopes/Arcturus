import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysLicense } from 'app/shared/model/sys-license.model';
import { SysLicenseService } from './sys-license.service';
import { SysLicenseComponent } from './sys-license.component';
import { SysLicenseDetailComponent } from './sys-license-detail.component';
import { SysLicenseUpdateComponent } from './sys-license-update.component';
import { SysLicenseDeletePopupComponent } from './sys-license-delete-dialog.component';
import { ISysLicense } from 'app/shared/model/sys-license.model';

@Injectable({ providedIn: 'root' })
export class SysLicenseResolve implements Resolve<ISysLicense> {
  constructor(private service: SysLicenseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysLicense> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysLicense>) => response.ok),
        map((sysLicense: HttpResponse<SysLicense>) => sysLicense.body)
      );
    }
    return of(new SysLicense());
  }
}

export const sysLicenseRoute: Routes = [
  {
    path: '',
    component: SysLicenseComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysLicenses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysLicenseDetailComponent,
    resolve: {
      sysLicense: SysLicenseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysLicenses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysLicenseUpdateComponent,
    resolve: {
      sysLicense: SysLicenseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysLicenses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysLicenseUpdateComponent,
    resolve: {
      sysLicense: SysLicenseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysLicenses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysLicensePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysLicenseDeletePopupComponent,
    resolve: {
      sysLicense: SysLicenseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysLicenses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
