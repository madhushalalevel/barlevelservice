import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BarLevelServiceSharedModule } from 'app/shared';
import {
  BranchComponent,
  BranchDetailComponent,
  BranchUpdateComponent,
  BranchDeletePopupComponent,
  BranchDeleteDialogComponent,
  branchRoute,
  branchPopupRoute
} from './';

const ENTITY_STATES = [...branchRoute, ...branchPopupRoute];

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BranchComponent, BranchDetailComponent, BranchUpdateComponent, BranchDeleteDialogComponent, BranchDeletePopupComponent],
  entryComponents: [BranchComponent, BranchUpdateComponent, BranchDeleteDialogComponent, BranchDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceBranchModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
