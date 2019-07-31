import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysPersonTypeComponent,
  SysPersonTypeDetailComponent,
  SysPersonTypeUpdateComponent,
  SysPersonTypeDeletePopupComponent,
  SysPersonTypeDeleteDialogComponent,
  sysPersonTypeRoute,
  sysPersonTypePopupRoute
} from './';

const ENTITY_STATES = [...sysPersonTypeRoute, ...sysPersonTypePopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysPersonTypeComponent,
    SysPersonTypeDetailComponent,
    SysPersonTypeUpdateComponent,
    SysPersonTypeDeleteDialogComponent,
    SysPersonTypeDeletePopupComponent
  ],
  entryComponents: [
    SysPersonTypeComponent,
    SysPersonTypeUpdateComponent,
    SysPersonTypeDeleteDialogComponent,
    SysPersonTypeDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysPersonTypeModule {}
