import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SysModule } from 'app/shared/model/sys-module.model';
import { SysModuleService } from './sys-module.service';
import { SysModuleComponent } from './sys-module.component';
import { SysModuleDetailComponent } from './sys-module-detail.component';
import { SysModuleUpdateComponent } from './sys-module-update.component';
import { SysModuleDeletePopupComponent } from './sys-module-delete-dialog.component';
import { ISysModule } from 'app/shared/model/sys-module.model';

@Injectable({ providedIn: 'root' })
export class SysModuleResolve implements Resolve<ISysModule> {
  constructor(private service: SysModuleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISysModule> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SysModule>) => response.ok),
        map((sysModule: HttpResponse<SysModule>) => sysModule.body)
      );
    }
    return of(new SysModule());
  }
}

export const sysModuleRoute: Routes = [
  {
    path: '',
    component: SysModuleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysModules'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SysModuleDetailComponent,
    resolve: {
      sysModule: SysModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysModules'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SysModuleUpdateComponent,
    resolve: {
      sysModule: SysModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysModules'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SysModuleUpdateComponent,
    resolve: {
      sysModule: SysModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysModules'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sysModulePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SysModuleDeletePopupComponent,
    resolve: {
      sysModule: SysModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'SysModules'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
