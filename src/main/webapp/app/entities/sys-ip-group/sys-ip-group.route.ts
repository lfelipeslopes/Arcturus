import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysIpGroup } from 'app/shared/model/sys-ip-group.model';
import { SysIpGroupService } from './sys-ip-group.service';
import { SysIpGroupComponent } from './sys-ip-group.component';
import { SysIpGroupDetailComponent } from './sys-ip-group-detail.component';
import { SysIpGroupUpdateComponent } from './sys-ip-group-update.component';
import { SysIpGroupDeletePopupComponent } from './sys-ip-group-delete-dialog.component';
import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';

@Injectable({ providedIn: 'root' })
export class SysIpGroupResolve implements Resolve<ISysIpGroup> {
  constructor(private service: SysIpGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysIpGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysIpGroup>) => response.ok),
        map((sysIpGroup: HttpResponse<SysIpGroup>) => sysIpGroup.body)
      );
    }
    return of(new SysIpGroup());
  }
}

export const sysIpGroupRoute: Routes = [
  {
    path: '',
    component: SysIpGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysIpGroupDetailComponent,
    resolve: {
      sysIpGroup: SysIpGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysIpGroupUpdateComponent,
    resolve: {
      sysIpGroup: SysIpGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysIpGroupUpdateComponent,
    resolve: {
      sysIpGroup: SysIpGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysIpGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysIpGroupDeletePopupComponent,
    resolve: {
      sysIpGroup: SysIpGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
