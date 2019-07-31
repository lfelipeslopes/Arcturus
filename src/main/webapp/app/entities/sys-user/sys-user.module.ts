import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysUserComponent,
  SysUserDetailComponent,
  SysUserUpdateComponent,
  SysUserDeletePopupComponent,
  SysUserDeleteDialogComponent,
  sysUserRoute,
  sysUserPopupRoute
} from './';

const ENTITY_STATES = [...sysUserRoute, ...sysUserPopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysUserComponent,
    SysUserDetailComponent,
    SysUserUpdateComponent,
    SysUserDeleteDialogComponent,
    SysUserDeletePopupComponent
  ],
  entryComponents: [SysUserComponent, SysUserUpdateComponent, SysUserDeleteDialogComponent, SysUserDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysUserModule {}
