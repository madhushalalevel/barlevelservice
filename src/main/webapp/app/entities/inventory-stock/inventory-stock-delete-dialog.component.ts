import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryStock } from 'app/shared/model/inventory-stock.model';
import { InventoryStockService } from './inventory-stock.service';

@Component({
  templateUrl: './inventory-stock-delete-dialog.component.html'
})
export class InventoryStockDeleteDialogComponent {
  inventoryStock?: IInventoryStock;

  constructor(
    protected inventoryStockService: InventoryStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inventoryStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('inventoryStockListModification');
      this.activeModal.close();
    });
  }
}
