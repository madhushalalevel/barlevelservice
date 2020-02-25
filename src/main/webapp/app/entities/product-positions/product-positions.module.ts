import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  ProductPositionsComponent,
  ProductPositionsDetailComponent,
  ProductPositionsUpdateComponent,
  ProductPositionsDeletePopupComponent,
  ProductPositionsDeleteDialogComponent,
  productPositionsRoute,
  productPositionsPopupRoute
} from './';

const ENTITY_STATES = [...productPositionsRoute, ...productPositionsPopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductPositionsComponent,
    ProductPositionsDetailComponent,
    ProductPositionsUpdateComponent,
    ProductPositionsDeleteDialogComponent,
    ProductPositionsDeletePopupComponent
  ],
  entryComponents: [
    ProductPositionsComponent,
    ProductPositionsUpdateComponent,
    ProductPositionsDeleteDialogComponent,
    ProductPositionsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceProductPositionsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
