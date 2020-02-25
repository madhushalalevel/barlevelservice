import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarLevelServiceSharedModule } from 'app/shared/shared.module';
import { ProductPositionsComponent } from './product-positions.component';
import { ProductPositionsDetailComponent } from './product-positions-detail.component';
import { ProductPositionsUpdateComponent } from './product-positions-update.component';
import { ProductPositionsDeleteDialogComponent } from './product-positions-delete-dialog.component';
import { productPositionsRoute } from './product-positions.route';

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(productPositionsRoute)],
  declarations: [
    ProductPositionsComponent,
    ProductPositionsDetailComponent,
    ProductPositionsUpdateComponent,
    ProductPositionsDeleteDialogComponent
  ],
  entryComponents: [ProductPositionsDeleteDialogComponent]
})
export class BarLevelServiceProductPositionsModule {}
