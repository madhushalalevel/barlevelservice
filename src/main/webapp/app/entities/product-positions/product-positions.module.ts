import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarlevelserviceSharedModule } from 'app/shared/shared.module';
import { ProductPositionsComponent } from './product-positions.component';
import { ProductPositionsDetailComponent } from './product-positions-detail.component';
import { ProductPositionsUpdateComponent } from './product-positions-update.component';
import { ProductPositionsDeleteDialogComponent } from './product-positions-delete-dialog.component';
import { productPositionsRoute } from './product-positions.route';

@NgModule({
  imports: [BarlevelserviceSharedModule, RouterModule.forChild(productPositionsRoute)],
  declarations: [
    ProductPositionsComponent,
    ProductPositionsDetailComponent,
    ProductPositionsUpdateComponent,
    ProductPositionsDeleteDialogComponent
  ],
  entryComponents: [ProductPositionsDeleteDialogComponent]
})
export class BarlevelserviceProductPositionsModule {}
