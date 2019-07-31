import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysGroup } from 'app/shared/model/sys-group.model';
import { SysGroupService } from './sys-group.service';
import { SysGroupComponent } from './sys-group.component';
import { SysGroupDetailComponent } from './sys-group-detail.component';
import { SysGroupUpdateComponent } from './sys-group-update.component';
import { SysGroupDeletePopupComponent } from './sys-group-delete-dialog.component';
import { ISysGroup } from 'app/shared/model/sys-group.model';

@Injectable({ providedIn: 'root' })
export class SysGroupResolve implements Resolve<ISysGroup> {
  constructor(private service: SysGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysGroup>) => response.ok),
        map((sysGroup: HttpResponse<SysGroup>) => sysGroup.body)
      );
    }
    return of(new SysGroup());
  }
}

export const sysGroupRoute: Routes = [
  {
    path: '',
    component: SysGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysGroupDetailComponent,
    resolve: {
      sysGroup: SysGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysGroupUpdateComponent,
    resolve: {
      sysGroup: SysGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysGroups'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysGroupUpdateComponent,
    resolve: {
      sysGroup: SysGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysGroups'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysGroupDeletePopupComponent,
    resolve: {
      sysGroup: SysGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysGroups'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
