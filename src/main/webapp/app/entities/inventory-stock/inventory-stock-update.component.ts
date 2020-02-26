import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventoryStock, InventoryStock } from 'app/shared/model/inventory-stock.model';
import { InventoryStockService } from './inventory-stock.service';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory/inventory.service';

@Component({
  selector: 'jhi-inventory-stock-update',
  templateUrl: './inventory-stock-update.component.html'
})
export class InventoryStockUpdateComponent implements OnInit {
  isSaving = false;
  inventories: IInventory[] = [];

  editForm = this.fb.group({
    id: [],
    stockCount: [],
    datetime: [],
    inventoryId: []
  });

  constructor(
    protected inventoryStockService: InventoryStockService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryStock }) => {
      if (!inventoryStock.id) {
        const today = moment().startOf('day');
        inventoryStock.datetime = today;
      }

      this.updateForm(inventoryStock);

      this.inventoryService
        .query({ filter: 'inventorystock-is-null' })
        .pipe(
          map((res: HttpResponse<IInventory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IInventory[]) => {
          if (!inventoryStock.inventoryId) {
            this.inventories = resBody;
          } else {
            this.inventoryService
              .find(inventoryStock.inventoryId)
              .pipe(
                map((subRes: HttpResponse<IInventory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IInventory[]) => (this.inventories = concatRes));
          }
        });
    });
  }

  updateForm(inventoryStock: IInventoryStock): void {
    this.editForm.patchValue({
      id: inventoryStock.id,
      stockCount: inventoryStock.stockCount,
      datetime: inventoryStock.datetime ? inventoryStock.datetime.format(DATE_TIME_FORMAT) : null,
      inventoryId: inventoryStock.inventoryId
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
      stockCount: this.editForm.get(['stockCount'])!.value,
      datetime: this.editForm.get(['datetime'])!.value ? moment(this.editForm.get(['datetime'])!.value, DATE_TIME_FORMAT) : undefined,
      inventoryId: this.editForm.get(['inventoryId'])!.value
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

  trackById(index: number, item: IInventory): any {
    return item.id;
  }
}
