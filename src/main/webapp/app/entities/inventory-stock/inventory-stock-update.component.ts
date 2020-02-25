import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
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
  inventoryStock: IInventoryStock;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    inventoryId: [],
    productID: [],
    stockCount: [],
    datetime: []
  });

  constructor(protected inventoryStockService: InventoryStockService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventoryStock }) => {
      this.updateForm(inventoryStock);
      this.inventoryStock = inventoryStock;
    });
  }

  updateForm(inventoryStock: IInventoryStock) {
    this.editForm.patchValue({
      id: inventoryStock.id,
      inventoryId: inventoryStock.inventoryId,
      productID: inventoryStock.productID,
      stockCount: inventoryStock.stockCount,
      datetime: inventoryStock.datetime
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventoryStock = this.createFromForm();
    if (inventoryStock.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryStockService.update(inventoryStock));
    } else {
      this.subscribeToSaveResponse(this.inventoryStockService.create(inventoryStock));
    }
  }

  private createFromForm(): IInventoryStock {
    const entity = {
      ...new InventoryStock(),
      id: this.editForm.get(['id']).value,
      inventoryId: this.editForm.get(['inventoryId']).value,
      productID: this.editForm.get(['productID']).value,
      stockCount: this.editForm.get(['stockCount']).value,
      datetime: this.editForm.get(['datetime']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryStock>>) {
    result.subscribe((res: HttpResponse<IInventoryStock>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
