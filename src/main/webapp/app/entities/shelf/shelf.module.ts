import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  ShelfComponent,
  ShelfDetailComponent,
  ShelfUpdateComponent,
  ShelfDeletePopupComponent,
  ShelfDeleteDialogComponent,
  shelfRoute,
  shelfPopupRoute
} from './';

const ENTITY_STATES = [...shelfRoute, ...shelfPopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ShelfComponent, ShelfDetailComponent, ShelfUpdateComponent, ShelfDeleteDialogComponent, ShelfDeletePopupComponent],
  entryComponents: [ShelfComponent, ShelfUpdateComponent, ShelfDeleteDialogComponent, ShelfDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceShelfModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
