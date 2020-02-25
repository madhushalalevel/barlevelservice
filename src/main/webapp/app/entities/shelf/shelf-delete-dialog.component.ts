import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShelf } from 'app/shared/model/shelf.model';
import { ShelfService } from './shelf.service';

@Component({
  selector: 'jhi-shelf-delete-dialog',
  templateUrl: './shelf-delete-dialog.component.html'
})
export class ShelfDeleteDialogComponent {
  shelf: IShelf;

  constructor(protected shelfService: ShelfService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shelfService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'shelfListModification',
        content: 'Deleted an shelf'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-shelf-delete-popup',
  template: ''
})
export class ShelfDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shelf }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ShelfDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.shelf = shelf;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/shelf', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/shelf', { outlets: { popup: null } }]);
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
