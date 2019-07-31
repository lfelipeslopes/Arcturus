import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';
import { SysIpGroupItemService } from './sys-ip-group-item.service';
import { SysIpGroupItemComponent } from './sys-ip-group-item.component';
import { SysIpGroupItemDetailComponent } from './sys-ip-group-item-detail.component';
import { SysIpGroupItemUpdateComponent } from './sys-ip-group-item-update.component';
import { SysIpGroupItemDeletePopupComponent } from './sys-ip-group-item-delete-dialog.component';
import { ISysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';

@Injectable({ providedIn: 'root' })
export class SysIpGroupItemResolve implements Resolve<ISysIpGroupItem> {
  constructor(private service: SysIpGroupItemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysIpGroupItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysIpGroupItem>) => response.ok),
        map((sysIpGroupItem: HttpResponse<SysIpGroupItem>) => sysIpGroupItem.body)
      );
    }
    return of(new SysIpGroupItem());
  }
}

export const sysIpGroupItemRoute: Routes = [
  {
    path: '',
    component: SysIpGroupItemComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroupItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysIpGroupItemDetailComponent,
    resolve: {
      sysIpGroupItem: SysIpGroupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroupItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysIpGroupItemUpdateComponent,
    resolve: {
      sysIpGroupItem: SysIpGroupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroupItems'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysIpGroupItemUpdateComponent,
    resolve: {
      sysIpGroupItem: SysIpGroupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroupItems'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysIpGroupItemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysIpGroupItemDeletePopupComponent,
    resolve: {
      sysIpGroupItem: SysIpGroupItemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysIpGroupItems'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
