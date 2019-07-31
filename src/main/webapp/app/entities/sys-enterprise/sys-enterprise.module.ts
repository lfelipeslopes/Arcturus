import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysEnterpriseComponent,
  SysEnterpriseDetailComponent,
  SysEnterpriseUpdateComponent,
  SysEnterpriseDeletePopupComponent,
  SysEnterpriseDeleteDialogComponent,
  sysEnterpriseRoute,
  sysEnterprisePopupRoute
} from './';

const ENTITY_STATES = [...sysEnterpriseRoute, ...sysEnterprisePopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysEnterpriseComponent,
    SysEnterpriseDetailComponent,
    SysEnterpriseUpdateComponent,
    SysEnterpriseDeleteDialogComponent,
    SysEnterpriseDeletePopupComponent
  ],
  entryComponents: [
    SysEnterpriseComponent,
    SysEnterpriseUpdateComponent,
    SysEnterpriseDeleteDialogComponent,
    SysEnterpriseDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysEnterpriseModule {}
