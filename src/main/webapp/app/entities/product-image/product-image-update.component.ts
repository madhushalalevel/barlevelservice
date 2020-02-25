import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProductImage, ProductImage } from 'app/shared/model/product-image.model';
import { ProductImageService } from './product-image.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
  selector: 'jhi-product-image-update',
  templateUrl: './product-image-update.component.html'
})
export class ProductImageUpdateComponent implements OnInit {
  productImage: IProductImage;
  isSaving: boolean;

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    imageUrl: [],
    image: [],
    imageContentType: [],
    productId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productImageService: ProductImageService,
    protected productService: ProductService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productImage }) => {
      this.updateForm(productImage);
      this.productImage = productImage;
    });
    this.productService
      .query({ filter: 'productimage-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe(
        (res: IProduct[]) => {
          if (!this.productImage.productId) {
            this.products = res;
          } else {
            this.productService
              .find(this.productImage.productId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProduct>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProduct>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProduct) => (this.products = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(productImage: IProductImage) {
    this.editForm.patchValue({
      id: productImage.id,
      imageUrl: productImage.imageUrl,
      image: productImage.image,
      imageContentType: productImage.imageContentType,
      productId: productImage.productId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productImage = this.createFromForm();
    if (productImage.id !== undefined) {
      this.subscribeToSaveResponse(this.productImageService.update(productImage));
    } else {
      this.subscribeToSaveResponse(this.productImageService.create(productImage));
    }
  }

  private createFromForm(): IProductImage {
    const entity = {
      ...new ProductImage(),
      id: this.editForm.get(['id']).value,
      imageUrl: this.editForm.get(['imageUrl']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value,
      productId: this.editForm.get(['productId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductImage>>) {
    result.subscribe((res: HttpResponse<IProductImage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
