import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryStock } from 'app/shared/model/inventory-stock.model';

@Component({
  selector: 'jhi-inventory-stock-detail',
  templateUrl: './inventory-stock-detail.component.html'
})
export class InventoryStockDetailComponent implements OnInit {
  inventoryStock: IInventoryStock;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventoryStock }) => {
      this.inventoryStock = inventoryStock;
    });
  }

  previousState() {
    window.history.back();
  }
}
