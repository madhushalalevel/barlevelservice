import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductImage } from 'app/shared/model/product-image.model';

@Component({
  selector: 'jhi-product-image-detail',
  templateUrl: './product-image-detail.component.html'
})
export class ProductImageDetailComponent implements OnInit {
  productImage: IProductImage | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productImage }) => (this.productImage = productImage));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
