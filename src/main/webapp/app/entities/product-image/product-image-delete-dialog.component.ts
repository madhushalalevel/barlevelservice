import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductImage } from 'app/shared/model/product-image.model';
import { ProductImageService } from './product-image.service';

@Component({
  selector: 'jhi-product-image-delete-dialog',
  templateUrl: './product-image-delete-dialog.component.html'
})
export class ProductImageDeleteDialogComponent {
  productImage: IProductImage;

  constructor(
    protected productImageService: ProductImageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productImageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productImageListModification',
        content: 'Deleted an productImage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-image-delete-popup',
  template: ''
})
export class ProductImageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productImage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductImageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productImage = productImage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-image', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-image', { outlets: { popup: null } }]);
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
