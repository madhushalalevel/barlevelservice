import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductVariousPositions, ProductVariousPositions } from 'app/shared/model/product-various-positions.model';
import { ProductVariousPositionsService } from './product-various-positions.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-product-various-positions-update',
  templateUrl: './product-various-positions-update.component.html'
})
export class ProductVariousPositionsUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    xAxis: [],
    yAxis: [],
    order: [],
    productId: []
  });

  constructor(
    protected productVariousPositionsService: ProductVariousPositionsService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productVariousPositions }) => {
      this.updateForm(productVariousPositions);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(productVariousPositions: IProductVariousPositions): void {
    this.editForm.patchValue({
      id: productVariousPositions.id,
      xAxis: productVariousPositions.xAxis,
      yAxis: productVariousPositions.yAxis,
      order: productVariousPositions.order,
      productId: productVariousPositions.productId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productVariousPositions = this.createFromForm();
    if (productVariousPositions.id !== undefined) {
      this.subscribeToSaveResponse(this.productVariousPositionsService.update(productVariousPositions));
    } else {
      this.subscribeToSaveResponse(this.productVariousPositionsService.create(productVariousPositions));
    }
  }

  private createFromForm(): IProductVariousPositions {
    return {
      ...new ProductVariousPositions(),
      id: this.editForm.get(['id'])!.value,
      xAxis: this.editForm.get(['xAxis'])!.value,
      yAxis: this.editForm.get(['yAxis'])!.value,
      order: this.editForm.get(['order'])!.value,
      productId: this.editForm.get(['productId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductVariousPositions>>): void {
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

  trackById(index: number, item: IProduct): any {
    return item.id;
  }
}
