import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductVariousPositions } from 'app/shared/model/product-various-positions.model';
import { ProductVariousPositionsService } from './product-various-positions.service';

@Component({
  templateUrl: './product-various-positions-delete-dialog.component.html'
})
export class ProductVariousPositionsDeleteDialogComponent {
  productVariousPositions?: IProductVariousPositions;

  constructor(
    protected productVariousPositionsService: ProductVariousPositionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productVariousPositionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productVariousPositionsListModification');
      this.activeModal.close();
    });
  }
}
