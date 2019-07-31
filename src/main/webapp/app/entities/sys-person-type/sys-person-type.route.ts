import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysPersonType } from 'app/shared/model/sys-person-type.model';
import { SysPersonTypeService } from './sys-person-type.service';
import { SysPersonTypeComponent } from './sys-person-type.component';
import { SysPersonTypeDetailComponent } from './sys-person-type-detail.component';
import { SysPersonTypeUpdateComponent } from './sys-person-type-update.component';
import { SysPersonTypeDeletePopupComponent } from './sys-person-type-delete-dialog.component';
import { ISysPersonType } from 'app/shared/model/sys-person-type.model';

@Injectable({ providedIn: 'root' })
export class SysPersonTypeResolve implements Resolve<ISysPersonType> {
  constructor(private service: SysPersonTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysPersonType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysPersonType>) => response.ok),
        map((sysPersonType: HttpResponse<SysPersonType>) => sysPersonType.body)
      );
    }
    return of(new SysPersonType());
  }
}

export const sysPersonTypeRoute: Routes = [
  {
    path: '',
    component: SysPersonTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPersonTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysPersonTypeDetailComponent,
    resolve: {
      sysPersonType: SysPersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPersonTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysPersonTypeUpdateComponent,
    resolve: {
      sysPersonType: SysPersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPersonTypes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysPersonTypeUpdateComponent,
    resolve: {
      sysPersonType: SysPersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPersonTypes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysPersonTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysPersonTypeDeletePopupComponent,
    resolve: {
      sysPersonType: SysPersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPersonTypes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
