import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarlevelserviceSharedModule } from 'app/shared/shared.module';
import { UsageComponent } from './usage.component';
import { UsageDetailComponent } from './usage-detail.component';
import { UsageUpdateComponent } from './usage-update.component';
import { UsageDeleteDialogComponent } from './usage-delete-dialog.component';
import { usageRoute } from './usage.route';

@NgModule({
  imports: [BarlevelserviceSharedModule, RouterModule.forChild(usageRoute)],
  declarations: [UsageComponent, UsageDetailComponent, UsageUpdateComponent, UsageDeleteDialogComponent],
  entryComponents: [UsageDeleteDialogComponent]
})
export class BarlevelserviceUsageModule {}
