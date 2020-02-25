import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BarLevelServiceSharedLibsModule, BarLevelServiceSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BarLevelServiceSharedLibsModule, BarLevelServiceSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BarLevelServiceSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceSharedModule {
  static forRoot() {
    return {
      ngModule: BarLevelServiceSharedModule
    };
  }
}
