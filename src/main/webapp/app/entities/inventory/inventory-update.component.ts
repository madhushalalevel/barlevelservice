import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IInventory, Inventory } from 'app/shared/model/inventory.model';
import { InventoryService } from './inventory.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IBranch } from 'app/shared/model/branch.model';
import { BranchService } from 'app/entities/branch/branch.service';
import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from 'app/entities/zone/zone.service';
import { IShelf } from 'app/shared/model/shelf.model';
import { ShelfService } from 'app/entities/shelf/shelf.service';

type SelectableEntity = IProduct | IBranch | IZone | IShelf;

@Component({
  selector: 'jhi-inventory-update',
  templateUrl: './inventory-update.component.html'
})
export class InventoryUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  branches: IBranch[] = [];
  zones: IZone[] = [];
  shelves: IShelf[] = [];

  editForm = this.fb.group({
    id: [],
    tennentID: [],
    currentStockCount: [],
    productId: [],
    branchId: [],
    zoneId: [],
    shelfId: []
  });

  constructor(
    protected inventoryService: InventoryService,
    protected productService: ProductService,
    protected branchService: BranchService,
    protected zoneService: ZoneService,
    protected shelfService: ShelfService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventory }) => {
      this.updateForm(inventory);

      this.productService
        .query({ filter: 'inventory-is-null' })
        .pipe(
          map((res: HttpResponse<IProduct[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IProduct[]) => {
          if (!inventory.productId) {
            this.products = resBody;
          } else {
            this.productService
              .find(inventory.productId)
              .pipe(
                map((subRes: HttpResponse<IProduct>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProduct[]) => (this.products = concatRes));
          }
        });

      this.branchService.query().subscribe((res: HttpResponse<IBranch[]>) => (this.branches = res.body || []));

      this.zoneService.query().subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));

      this.shelfService.query().subscribe((res: HttpResponse<IShelf[]>) => (this.shelves = res.body || []));
    });
  }

  updateForm(inventory: IInventory): void {
    this.editForm.patchValue({
      id: inventory.id,
      tennentID: inventory.tennentID,
      currentStockCount: inventory.currentStockCount,
      productId: inventory.productId,
      branchId: inventory.branchId,
      zoneId: inventory.zoneId,
      shelfId: inventory.shelfId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventory = this.createFromForm();
    if (inventory.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryService.update(inventory));
    } else {
      this.subscribeToSaveResponse(this.inventoryService.create(inventory));
    }
  }

  private createFromForm(): IInventory {
    return {
      ...new Inventory(),
      id: this.editForm.get(['id'])!.value,
      tennentID: this.editForm.get(['tennentID'])!.value,
      currentStockCount: this.editForm.get(['currentStockCount'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      branchId: this.editForm.get(['branchId'])!.value,
      zoneId: this.editForm.get(['zoneId'])!.value,
      shelfId: this.editForm.get(['shelfId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventory>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
