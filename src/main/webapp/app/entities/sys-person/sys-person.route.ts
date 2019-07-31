import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysPerson } from 'app/shared/model/sys-person.model';
import { SysPersonService } from './sys-person.service';
import { SysPersonComponent } from './sys-person.component';
import { SysPersonDetailComponent } from './sys-person-detail.component';
import { SysPersonUpdateComponent } from './sys-person-update.component';
import { SysPersonDeletePopupComponent } from './sys-person-delete-dialog.component';
import { ISysPerson } from 'app/shared/model/sys-person.model';

@Injectable({ providedIn: 'root' })
export class SysPersonResolve implements Resolve<ISysPerson> {
  constructor(private service: SysPersonService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysPerson> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysPerson>) => response.ok),
        map((sysPerson: HttpResponse<SysPerson>) => sysPerson.body)
      );
    }
    return of(new SysPerson());
  }
}

export const sysPersonRoute: Routes = [
  {
    path: '',
    component: SysPersonComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPeople'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysPersonDetailComponent,
    resolve: {
      sysPerson: SysPersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPeople'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysPersonUpdateComponent,
    resolve: {
      sysPerson: SysPersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPeople'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysPersonUpdateComponent,
    resolve: {
      sysPerson: SysPersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPeople'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysPersonPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysPersonDeletePopupComponent,
    resolve: {
      sysPerson: SysPersonResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysPeople'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
