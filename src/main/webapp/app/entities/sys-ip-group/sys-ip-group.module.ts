import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysIpGroupComponent,
  SysIpGroupDetailComponent,
  SysIpGroupUpdateComponent,
  SysIpGroupDeletePopupComponent,
  SysIpGroupDeleteDialogComponent,
  sysIpGroupRoute,
  sysIpGroupPopupRoute
} from './';

const ENTITY_STATES = [...sysIpGroupRoute, ...sysIpGroupPopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysIpGroupComponent,
    SysIpGroupDetailComponent,
    SysIpGroupUpdateComponent,
    SysIpGroupDeleteDialogComponent,
    SysIpGroupDeletePopupComponent
  ],
  entryComponents: [SysIpGroupComponent, SysIpGroupUpdateComponent, SysIpGroupDeleteDialogComponent, SysIpGroupDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysIpGroupModule {}
