import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  ProductVariousPositionsComponent,
  ProductVariousPositionsDetailComponent,
  ProductVariousPositionsUpdateComponent,
  ProductVariousPositionsDeletePopupComponent,
  ProductVariousPositionsDeleteDialogComponent,
  productVariousPositionsRoute,
  productVariousPositionsPopupRoute
} from './';

const ENTITY_STATES = [...productVariousPositionsRoute, ...productVariousPositionsPopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductVariousPositionsComponent,
    ProductVariousPositionsDetailComponent,
    ProductVariousPositionsUpdateComponent,
    ProductVariousPositionsDeleteDialogComponent,
    ProductVariousPositionsDeletePopupComponent
  ],
  entryComponents: [
    ProductVariousPositionsComponent,
    ProductVariousPositionsUpdateComponent,
    ProductVariousPositionsDeleteDialogComponent,
    ProductVariousPositionsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceProductVariousPositionsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
