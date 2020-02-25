import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsage } from 'app/shared/model/usage.model';
import { UsageService } from './usage.service';

@Component({
  templateUrl: './usage-delete-dialog.component.html'
})
export class UsageDeleteDialogComponent {
  usage?: IUsage;

  constructor(protected usageService: UsageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('usageListModification');
      this.activeModal.close();
    });
  }
}
