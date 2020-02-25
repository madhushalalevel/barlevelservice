import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarLevelServiceSharedModule } from 'app/shared/shared.module';
import { UsageComponent } from './usage.component';
import { UsageDetailComponent } from './usage-detail.component';
import { UsageUpdateComponent } from './usage-update.component';
import { UsageDeleteDialogComponent } from './usage-delete-dialog.component';
import { usageRoute } from './usage.route';

@NgModule({
  imports: [BarLevelServiceSharedModule, RouterModule.forChild(usageRoute)],
  declarations: [UsageComponent, UsageDetailComponent, UsageUpdateComponent, UsageDeleteDialogComponent],
  entryComponents: [UsageDeleteDialogComponent]
})
export class BarLevelServiceUsageModule {}
