import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysIpGroupItemComponent,
  SysIpGroupItemDetailComponent,
  SysIpGroupItemUpdateComponent,
  SysIpGroupItemDeletePopupComponent,
  SysIpGroupItemDeleteDialogComponent,
  sysIpGroupItemRoute,
  sysIpGroupItemPopupRoute
} from './';

const ENTITY_STATES = [...sysIpGroupItemRoute, ...sysIpGroupItemPopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysIpGroupItemComponent,
    SysIpGroupItemDetailComponent,
    SysIpGroupItemUpdateComponent,
    SysIpGroupItemDeleteDialogComponent,
    SysIpGroupItemDeletePopupComponent
  ],
  entryComponents: [
    SysIpGroupItemComponent,
    SysIpGroupItemUpdateComponent,
    SysIpGroupItemDeleteDialogComponent,
    SysIpGroupItemDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysIpGroupItemModule {}
