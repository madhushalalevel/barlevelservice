import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProductPositions, ProductPositions } from 'app/shared/model/product-positions.model';
import { ProductPositionsService } from './product-positions.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-product-positions-update',
  templateUrl: './product-positions-update.component.html'
})
export class ProductPositionsUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    position: [],
    updatedTime: [],
    productId: []
  });

  constructor(
    protected productPositionsService: ProductPositionsService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPositions }) => {
      if (!productPositions.id) {
        const today = moment().startOf('day');
        productPositions.updatedTime = today;
      }

      this.updateForm(productPositions);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(productPositions: IProductPositions): void {
    this.editForm.patchValue({
      id: productPositions.id,
      position: productPositions.position,
      updatedTime: productPositions.updatedTime ? productPositions.updatedTime.format(DATE_TIME_FORMAT) : null,
      productId: productPositions.productId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productPositions = this.createFromForm();
    if (productPositions.id !== undefined) {
      this.subscribeToSaveResponse(this.productPositionsService.update(productPositions));
    } else {
      this.subscribeToSaveResponse(this.productPositionsService.create(productPositions));
    }
  }

  private createFromForm(): IProductPositions {
    return {
      ...new ProductPositions(),
      id: this.editForm.get(['id'])!.value,
      position: this.editForm.get(['position'])!.value,
      updatedTime: this.editForm.get(['updatedTime'])!.value
        ? moment(this.editForm.get(['updatedTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      productId: this.editForm.get(['productId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPositions>>): void {
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
