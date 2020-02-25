import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInventory, Inventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';

@Component({
  selector: 'jhi-inventory-update',
  templateUrl: './inventory-update.component.html'
})
export class InventoryUpdateComponent implements OnInit {
  inventory: IInventory;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    productID: [],
    tennentID: [],
    branchID: [],
    zoneID: [],
    shelfID: [],
    currentStockCount: []
  });

  constructor(protected inventoryService: InventoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventory }) => {
      this.updateForm(inventory);
      this.inventory = inventory;
    });
  }

  updateForm(inventory: IInventory) {
    this.editForm.patchValue({
      id: inventory.id,
      productID: inventory.productID,
      tennentID: inventory.tennentID,
      branchID: inventory.branchID,
      zoneID: inventory.zoneID,
      shelfID: inventory.shelfID,
      currentStockCount: inventory.currentStockCount
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventory = this.createFromForm();
    if (inventory.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryService.update(inventory));
    } else {
      this.subscribeToSaveResponse(this.inventoryService.create(inventory));
    }
  }

  private createFromForm(): IInventory {
    const entity = {
      ...new Inventory(),
      id: this.editForm.get(['id']).value,
      productID: this.editForm.get(['productID']).value,
      tennentID: this.editForm.get(['tennentID']).value,
      branchID: this.editForm.get(['branchID']).value,
      zoneID: this.editForm.get(['zoneID']).value,
      shelfID: this.editForm.get(['shelfID']).value,
      currentStockCount: this.editForm.get(['currentStockCount']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventory>>) {
    result.subscribe((res: HttpResponse<IInventory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
