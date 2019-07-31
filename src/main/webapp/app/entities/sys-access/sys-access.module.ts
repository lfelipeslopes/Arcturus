import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysAccessComponent,
  SysAccessDetailComponent,
  SysAccessUpdateComponent,
  SysAccessDeletePopupComponent,
  SysAccessDeleteDialogComponent,
  sysAccessRoute,
  sysAccessPopupRoute
} from './';

const ENTITY_STATES = [...sysAccessRoute, ...sysAccessPopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysAccessComponent,
    SysAccessDetailComponent,
    SysAccessUpdateComponent,
    SysAccessDeleteDialogComponent,
    SysAccessDeletePopupComponent
  ],
  entryComponents: [SysAccessComponent, SysAccessUpdateComponent, SysAccessDeleteDialogComponent, SysAccessDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysAccessModule {}
