import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  InventoryStockComponent,
  InventoryStockDetailComponent,
  InventoryStockUpdateComponent,
  InventoryStockDeletePopupComponent,
  InventoryStockDeleteDialogComponent,
  inventoryStockRoute,
  inventoryStockPopupRoute
} from './';

const ENTITY_STATES = [...inventoryStockRoute, ...inventoryStockPopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InventoryStockComponent,
    InventoryStockDetailComponent,
    InventoryStockUpdateComponent,
    InventoryStockDeleteDialogComponent,
    InventoryStockDeletePopupComponent
  ],
  entryComponents: [
    InventoryStockComponent,
    InventoryStockUpdateComponent,
    InventoryStockDeleteDialogComponent,
    InventoryStockDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceInventoryStockModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
