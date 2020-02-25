import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  ProductImageComponent,
  ProductImageDetailComponent,
  ProductImageUpdateComponent,
  ProductImageDeletePopupComponent,
  ProductImageDeleteDialogComponent,
  productImageRoute,
  productImagePopupRoute
} from './';

const ENTITY_STATES = [...productImageRoute, ...productImagePopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductImageComponent,
    ProductImageDetailComponent,
    ProductImageUpdateComponent,
    ProductImageDeleteDialogComponent,
    ProductImageDeletePopupComponent
  ],
  entryComponents: [
    ProductImageComponent,
    ProductImageUpdateComponent,
    ProductImageDeleteDialogComponent,
    ProductImageDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceProductImageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
