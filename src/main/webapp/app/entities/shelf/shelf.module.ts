import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarlevelserviceSharedModule } from 'app/shared/shared.module';
import { ShelfComponent } from './shelf.component';
import { ShelfDetailComponent } from './shelf-detail.component';
import { ShelfUpdateComponent } from './shelf-update.component';
import { ShelfDeleteDialogComponent } from './shelf-delete-dialog.component';
import { shelfRoute } from './shelf.route';

@NgModule({
  imports: [BarlevelserviceSharedModule, RouterModule.forChild(shelfRoute)],
  declarations: [ShelfComponent, ShelfDetailComponent, ShelfUpdateComponent, ShelfDeleteDialogComponent],
  entryComponents: [ShelfDeleteDialogComponent]
})
export class BarlevelserviceShelfModule {}
