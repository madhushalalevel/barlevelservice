import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarLevelServiceSharedModule } from 'app/shared/shared.module';
import { ProductImageComponent } from './product-image.component';
import { ProductImageDetailComponent } from './product-image-detail.component';
import { ProductImageUpdateComponent } from './product-image-update.component';
import { ProductImageDeleteDialogComponent } from './product-image-delete-dialog.component';
import { productImageRoute } from './product-image.route';

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(productImageRoute)],
  declarations: [ProductImageComponent, ProductImageDetailComponent, ProductImageUpdateComponent, ProductImageDeleteDialogComponent],
  entryComponents: [ProductImageDeleteDialogComponent]
})
export class BarLevelServiceProductImageModule {}
