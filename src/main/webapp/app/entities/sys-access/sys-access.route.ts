import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysAccess } from 'app/shared/model/sys-access.model';
import { SysAccessService } from './sys-access.service';
import { SysAccessComponent } from './sys-access.component';
import { SysAccessDetailComponent } from './sys-access-detail.component';
import { SysAccessUpdateComponent } from './sys-access-update.component';
import { SysAccessDeletePopupComponent } from './sys-access-delete-dialog.component';
import { ISysAccess } from 'app/shared/model/sys-access.model';

@Injectable({ providedIn: 'root' })
export class SysAccessResolve implements Resolve<ISysAccess> {
  constructor(private service: SysAccessService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysAccess> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysAccess>) => response.ok),
        map((sysAccess: HttpResponse<SysAccess>) => sysAccess.body)
      );
    }
    return of(new SysAccess());
  }
}

export const sysAccessRoute: Routes = [
  {
    path: '',
    component: SysAccessComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysAccesses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysAccessDetailComponent,
    resolve: {
      sysAccess: SysAccessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysAccesses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysAccessUpdateComponent,
    resolve: {
      sysAccess: SysAccessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysAccesses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysAccessUpdateComponent,
    resolve: {
      sysAccess: SysAccessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysAccesses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysAccessPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysAccessDeletePopupComponent,
    resolve: {
      sysAccess: SysAccessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysAccesses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
