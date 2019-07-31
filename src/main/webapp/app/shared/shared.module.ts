import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ArcturusSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ArcturusSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ArcturusSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArcturusSharedModule {
  static forRoot() {
    return {
      ngModule: ArcturusSharedModule
    };
  }
}
