import { NgModule } from '@angular/core';

import { ArcturusSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [ArcturusSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [ArcturusSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ArcturusSharedCommonModule {}
