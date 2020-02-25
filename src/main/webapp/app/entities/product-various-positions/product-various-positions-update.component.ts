import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductVariousPositions, ProductVariousPositions } from 'app/shared/model/product-various-positions.model';
import { ProductVariousPositionsService } from './product-various-positions.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
  selector: 'jhi-product-various-positions-update',
  templateUrl: './product-various-positions-update.component.html'
})
export class ProductVariousPositionsUpdateComponent implements OnInit {
  productVariousPositions: IProductVariousPositions;
  isSaving: boolean;

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    xAxis: [],
    yAxis: [],
    order: [],
    productId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productVariousPositionsService: ProductVariousPositionsService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productVariousPositions }) => {
      this.updateForm(productVariousPositions);
      this.productVariousPositions = productVariousPositions;
    });
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productVariousPositions: IProductVariousPositions) {
    this.editForm.patchValue({
      id: productVariousPositions.id,
      xAxis: productVariousPositions.xAxis,
      yAxis: productVariousPositions.yAxis,
      order: productVariousPositions.order,
      productId: productVariousPositions.productId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productVariousPositions = this.createFromForm();
    if (productVariousPositions.id !== undefined) {
      this.subscribeToSaveResponse(this.productVariousPositionsService.update(productVariousPositions));
    } else {
      this.subscribeToSaveResponse(this.productVariousPositionsService.create(productVariousPositions));
    }
  }

  private createFromForm(): IProductVariousPositions {
    const entity = {
      ...new ProductVariousPositions(),
      id: this.editForm.get(['id']).value,
      xAxis: this.editForm.get(['xAxis']).value,
      yAxis: this.editForm.get(['yAxis']).value,
      order: this.editForm.get(['order']).value,
      productId: this.editForm.get(['productId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductVariousPositions>>) {
    result.subscribe((res: HttpResponse<IProductVariousPositions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }
}
