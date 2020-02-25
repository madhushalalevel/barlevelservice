import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  InventoryComponent,
  InventoryDetailComponent,
  InventoryUpdateComponent,
  InventoryDeletePopupComponent,
  InventoryDeleteDialogComponent,
  inventoryRoute,
  inventoryPopupRoute
} from './';

const ENTITY_STATES = [...inventoryRoute, ...inventoryPopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InventoryComponent,
    InventoryDetailComponent,
    InventoryUpdateComponent,
    InventoryDeleteDialogComponent,
    InventoryDeletePopupComponent
  ],
  entryComponents: [InventoryComponent, InventoryUpdateComponent, InventoryDeleteDialogComponent, InventoryDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceInventoryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
