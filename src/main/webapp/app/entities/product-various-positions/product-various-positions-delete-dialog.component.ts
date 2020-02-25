import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductVariousPositions } from 'app/shared/model/product-various-positions.model';
import { ProductVariousPositionsService } from './product-various-positions.service';

@Component({
  selector: 'jhi-product-various-positions-delete-dialog',
  templateUrl: './product-various-positions-delete-dialog.component.html'
})
export class ProductVariousPositionsDeleteDialogComponent {
  productVariousPositions: IProductVariousPositions;

  constructor(
    protected productVariousPositionsService: ProductVariousPositionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productVariousPositionsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productVariousPositionsListModification',
        content: 'Deleted an productVariousPositions'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-various-positions-delete-popup',
  template: ''
})
export class ProductVariousPositionsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productVariousPositions }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductVariousPositionsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.productVariousPositions = productVariousPositions;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-various-positions', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-various-positions', { outlets: { popup: null } }]);
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
