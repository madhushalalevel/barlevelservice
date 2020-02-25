import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventoryStock } from 'app/shared/model/inventory-stock.model';
import { InventoryStockService } from './inventory-stock.service';

@Component({
  selector: 'jhi-inventory-stock-delete-dialog',
  templateUrl: './inventory-stock-delete-dialog.component.html'
})
export class InventoryStockDeleteDialogComponent {
  inventoryStock: IInventoryStock;

  constructor(
    protected inventoryStockService: InventoryStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inventoryStockService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inventoryStockListModification',
        content: 'Deleted an inventoryStock'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inventory-stock-delete-popup',
  template: ''
})
export class InventoryStockDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryStock }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InventoryStockDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inventoryStock = inventoryStock;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inventory-stock', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inventory-stock', { outlets: { popup: null } }]);
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
