import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductImage } from 'app/shared/model/product-image.model';
import { ProductImageService } from './product-image.service';

@Component({
  templateUrl: './product-image-delete-dialog.component.html'
})
export class ProductImageDeleteDialogComponent {
  productImage?: IProductImage;

  constructor(
    protected productImageService: ProductImageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productImageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productImageListModification');
      this.activeModal.close();
    });
  }
}
