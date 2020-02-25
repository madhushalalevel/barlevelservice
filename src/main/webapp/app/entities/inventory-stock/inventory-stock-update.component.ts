import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInventoryStock, InventoryStock } from 'app/shared/model/inventory-stock.model';
import { InventoryStockService } from './inventory-stock.service';

@Component({
  selector: 'jhi-inventory-stock-update',
  templateUrl: './inventory-stock-update.component.html'
})
export class InventoryStockUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    inventoryId: [],
    productID: [],
    stockCount: [],
    datetime: []
  });

  constructor(protected inventoryStockService: InventoryStockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryStock }) => {
      this.updateForm(inventoryStock);
    });
  }

  updateForm(inventoryStock: IInventoryStock): void {
    this.editForm.patchValue({
      id: inventoryStock.id,
      inventoryId: inventoryStock.inventoryId,
      productID: inventoryStock.productID,
      stockCount: inventoryStock.stockCount,
      datetime: inventoryStock.datetime
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryStock = this.createFromForm();
    if (inventoryStock.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryStockService.update(inventoryStock));
    } else {
      this.subscribeToSaveResponse(this.inventoryStockService.create(inventoryStock));
    }
  }

  private createFromForm(): IInventoryStock {
    return {
      ...new InventoryStock(),
      id: this.editForm.get(['id'])!.value,
      inventoryId: this.editForm.get(['inventoryId'])!.value,
      productID: this.editForm.get(['productID'])!.value,
      stockCount: this.editForm.get(['stockCount'])!.value,
      datetime: this.editForm.get(['datetime'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryStock>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
