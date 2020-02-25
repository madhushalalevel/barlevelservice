import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IProductPositions, ProductPositions } from 'app/shared/model/product-positions.model';
import { ProductPositionsService } from './product-positions.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
  selector: 'jhi-product-positions-update',
  templateUrl: './product-positions-update.component.html'
})
export class ProductPositionsUpdateComponent implements OnInit {
  productPositions: IProductPositions;
  isSaving: boolean;

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    position: [],
    updatedTime: [],
    productId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productPositionsService: ProductPositionsService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productPositions }) => {
      this.updateForm(productPositions);
      this.productPositions = productPositions;
    });
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productPositions: IProductPositions) {
    this.editForm.patchValue({
      id: productPositions.id,
      position: productPositions.position,
      updatedTime: productPositions.updatedTime != null ? productPositions.updatedTime.format(DATE_TIME_FORMAT) : null,
      productId: productPositions.productId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productPositions = this.createFromForm();
    if (productPositions.id !== undefined) {
      this.subscribeToSaveResponse(this.productPositionsService.update(productPositions));
    } else {
      this.subscribeToSaveResponse(this.productPositionsService.create(productPositions));
    }
  }

  private createFromForm(): IProductPositions {
    const entity = {
      ...new ProductPositions(),
      id: this.editForm.get(['id']).value,
      position: this.editForm.get(['position']).value,
      updatedTime:
        this.editForm.get(['updatedTime']).value != null ? moment(this.editForm.get(['updatedTime']).value, DATE_TIME_FORMAT) : undefined,
      productId: this.editForm.get(['productId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPositions>>) {
    result.subscribe((res: HttpResponse<IProductPositions>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
