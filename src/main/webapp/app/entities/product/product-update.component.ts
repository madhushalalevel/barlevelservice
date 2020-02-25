import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory/inventory.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  inventories: IInventory[] = [];

  editForm = this.fb.group({
    id: [],
    productID: [],
    name: [],
    barCode: [],
    quantity: [],
    volume: [],
    type: [],
    subType: [],
    price: [],
    containerType: [],
    barCodeType: [],
    tenantId: [],
    inventoryId: []
  });

  constructor(
    protected productService: ProductService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.inventoryService.query().subscribe((res: HttpResponse<IInventory[]>) => (this.inventories = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      productID: product.productID,
      name: product.name,
      barCode: product.barCode,
      quantity: product.quantity,
      volume: product.volume,
      type: product.type,
      subType: product.subType,
      price: product.price,
      containerType: product.containerType,
      barCodeType: product.barCodeType,
      tenantId: product.tenantId,
      inventoryId: product.inventoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      productID: this.editForm.get(['productID'])!.value,
      name: this.editForm.get(['name'])!.value,
      barCode: this.editForm.get(['barCode'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      volume: this.editForm.get(['volume'])!.value,
      type: this.editForm.get(['type'])!.value,
      subType: this.editForm.get(['subType'])!.value,
      price: this.editForm.get(['price'])!.value,
      containerType: this.editForm.get(['containerType'])!.value,
      barCodeType: this.editForm.get(['barCodeType'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      inventoryId: this.editForm.get(['inventoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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
