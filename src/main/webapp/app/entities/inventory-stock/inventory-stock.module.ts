import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarlevelserviceSharedModule } from 'app/shared/shared.module';
import { InventoryStockComponent } from './inventory-stock.component';
import { InventoryStockDetailComponent } from './inventory-stock-detail.component';
import { InventoryStockUpdateComponent } from './inventory-stock-update.component';
import { InventoryStockDeleteDialogComponent } from './inventory-stock-delete-dialog.component';
import { inventoryStockRoute } from './inventory-stock.route';

@NgModule({
  imports: [BarlevelserviceSharedModule, RouterModule.forChild(inventoryStockRoute)],
  declarations: [
    InventoryStockComponent,
    InventoryStockDetailComponent,
    InventoryStockUpdateComponent,
    InventoryStockDeleteDialogComponent
  ],
  entryComponents: [InventoryStockDeleteDialogComponent]
})
export class BarlevelserviceInventoryStockModule {}
