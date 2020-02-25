import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarLevelServiceSharedModule } from 'app/shared/shared.module';
import { ProductVariousPositionsComponent } from './product-various-positions.component';
import { ProductVariousPositionsDetailComponent } from './product-various-positions-detail.component';
import { ProductVariousPositionsUpdateComponent } from './product-various-positions-update.component';
import { ProductVariousPositionsDeleteDialogComponent } from './product-various-positions-delete-dialog.component';
import { productVariousPositionsRoute } from './product-various-positions.route';

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(productVariousPositionsRoute)],
  declarations: [
    ProductVariousPositionsComponent,
    ProductVariousPositionsDetailComponent,
    ProductVariousPositionsUpdateComponent,
    ProductVariousPositionsDeleteDialogComponent
  ],
  entryComponents: [ProductVariousPositionsDeleteDialogComponent]
})
export class BarLevelServiceProductVariousPositionsModule {}
