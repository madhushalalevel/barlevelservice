import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUsage } from 'app/shared/model/usage.model';
import { UsageService } from './usage.service';

@Component({
  selector: 'jhi-usage-delete-dialog',
  templateUrl: './usage-delete-dialog.component.html'
})
export class UsageDeleteDialogComponent {
  usage: IUsage;

  constructor(protected usageService: UsageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.usageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'usageListModification',
        content: 'Deleted an usage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-usage-delete-popup',
  template: ''
})
export class UsageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ usage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UsageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.usage = usage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/usage', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/usage', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
