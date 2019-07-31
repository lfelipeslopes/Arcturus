import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArcturusSharedModule } from 'app/shared';
import {
  SysPersonComponent,
  SysPersonDetailComponent,
  SysPersonUpdateComponent,
  SysPersonDeletePopupComponent,
  SysPersonDeleteDialogComponent,
  sysPersonRoute,
  sysPersonPopupRoute
} from './';

const ENTITY_STATES = [...sysPersonRoute, ...sysPersonPopupRoute];

@NgModule({
  imports: [ArcturusSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SysPersonComponent,
    SysPersonDetailComponent,
    SysPersonUpdateComponent,
    SysPersonDeleteDialogComponent,
    SysPersonDeletePopupComponent
  ],
  entryComponents: [SysPersonComponent, SysPersonUpdateComponent, SysPersonDeleteDialogComponent, SysPersonDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSysPersonModule {}
