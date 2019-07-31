import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysGroupComponent,
  SysGroupDetailComponent,
  SysGroupUpdateComponent,
  SysGroupDeletePopupComponent,
  SysGroupDeleteDialogComponent,
  sysGroupRoute,
  sysGroupPopupRoute
} from './';

const ENTITY_STATES = [...sysGroupRoute, ...sysGroupPopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysGroupComponent,
    SysGroupDetailComponent,
    SysGroupUpdateComponent,
    SysGroupDeleteDialogComponent,
    SysGroupDeletePopupComponent
  ],
  entryComponents: [SysGroupComponent, SysGroupUpdateComponent, SysGroupDeleteDialogComponent, SysGroupDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysGroupModule {}
