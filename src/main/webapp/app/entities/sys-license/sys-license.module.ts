import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysLicenseComponent,
  SysLicenseDetailComponent,
  SysLicenseUpdateComponent,
  SysLicenseDeletePopupComponent,
  SysLicenseDeleteDialogComponent,
  sysLicenseRoute,
  sysLicensePopupRoute
} from './';

const ENTITY_STATES = [...sysLicenseRoute, ...sysLicensePopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysLicenseComponent,
    SysLicenseDetailComponent,
    SysLicenseUpdateComponent,
    SysLicenseDeleteDialogComponent,
    SysLicenseDeletePopupComponent
  ],
  entryComponents: [SysLicenseComponent, SysLicenseUpdateComponent, SysLicenseDeleteDialogComponent, SysLicenseDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysLicenseModule {}
