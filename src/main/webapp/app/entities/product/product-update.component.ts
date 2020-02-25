import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { IProductImage } from 'app/shared/model/product-image.model';
import { ProductImageService } from 'app/entities/product-image';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  product: IProduct;
  isSaving: boolean;

  productimages: IProductImage[];

  inventories: IInventory[];

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
    protected jhiAlertService: JhiAlertService,
    protected productService: ProductService,
    protected productImageService: ProductImageService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);
      this.product = product;
    });
    this.productImageService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductImage[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductImage[]>) => response.body)
      )
      .subscribe((res: IProductImage[]) => (this.productimages = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.inventoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInventory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInventory[]>) => response.body)
      )
      .subscribe((res: IInventory[]) => (this.inventories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(product: IProduct) {
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    const entity = {
      ...new Product(),
      id: this.editForm.get(['id']).value,
      productID: this.editForm.get(['productID']).value,
      name: this.editForm.get(['name']).value,
      barCode: this.editForm.get(['barCode']).value,
      quantity: this.editForm.get(['quantity']).value,
      volume: this.editForm.get(['volume']).value,
      type: this.editForm.get(['type']).value,
      subType: this.editForm.get(['subType']).value,
      price: this.editForm.get(['price']).value,
      containerType: this.editForm.get(['containerType']).value,
      barCodeType: this.editForm.get(['barCodeType']).value,
      tenantId: this.editForm.get(['tenantId']).value,
      inventoryId: this.editForm.get(['inventoryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
    result.subscribe((res: HttpResponse<IProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductImageById(index: number, item: IProductImage) {
    return item.id;
  }

  trackInventoryById(index: number, item: IInventory) {
    return item.id;
  }
}
