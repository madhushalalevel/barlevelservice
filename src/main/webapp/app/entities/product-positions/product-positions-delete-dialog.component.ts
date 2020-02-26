import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductPositions } from 'app/shared/model/product-positions.model';
import { ProductPositionsService } from './product-positions.service';

@Component({
  templateUrl: './product-positions-delete-dialog.component.html'
})
export class ProductPositionsDeleteDialogComponent {
  productPositions?: IProductPositions;

  constructor(
    protected productPositionsService: ProductPositionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productPositionsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productPositionsListModification');
      this.activeModal.close();
    });
  }
}
