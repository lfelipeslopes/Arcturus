import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysModuleComponent,
  SysModuleDetailComponent,
  SysModuleUpdateComponent,
  SysModuleDeletePopupComponent,
  SysModuleDeleteDialogComponent,
  sysModuleRoute,
  sysModulePopupRoute
} from './';

const ENTITY_STATES = [...sysModuleRoute, ...sysModulePopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysModuleComponent,
    SysModuleDetailComponent,
    SysModuleUpdateComponent,
    SysModuleDeleteDialogComponent,
    SysModuleDeletePopupComponent
  ],
  entryComponents: [SysModuleComponent, SysModuleUpdateComponent, SysModuleDeleteDialogComponent, SysModuleDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysModuleModule {}
