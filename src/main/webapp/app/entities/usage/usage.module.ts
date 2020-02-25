import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  UsageComponent,
  UsageDetailComponent,
  UsageUpdateComponent,
  UsageDeletePopupComponent,
  UsageDeleteDialogComponent,
  usageRoute,
  usagePopupRoute
} from './';

const ENTITY_STATES = [...usageRoute, ...usagePopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [UsageComponent, UsageDetailComponent, UsageUpdateComponent, UsageDeleteDialogComponent, UsageDeletePopupComponent],
  entryComponents: [UsageComponent, UsageUpdateComponent, UsageDeleteDialogComponent, UsageDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceUsageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
