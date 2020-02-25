import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPositions } from 'app/shared/model/product-positions.model';
import { ProductPositionsService } from './product-positions.service';

@Component({
  selector: 'jhi-product-positions-delete-dialog',
  templateUrl: './product-positions-delete-dialog.component.html'
})
export class ProductPositionsDeleteDialogComponent {
  productPositions: IProductPositions;

  constructor(
    protected productPositionsService: ProductPositionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productPositionsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productPositionsListModification',
        content: 'Deleted an productPositions'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-positions-delete-popup',
  template: ''
})
export class ProductPositionsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productPositions }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductPositionsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productPositions = productPositions;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-positions', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-positions', { outlets: { popup: null } }]);
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
