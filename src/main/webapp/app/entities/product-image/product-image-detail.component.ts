import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductImage } from 'app/shared/model/product-image.model';

@Component({
  selector: 'jhi-product-image-detail',
  templateUrl: './product-image-detail.component.html'
})
export class ProductImageDetailComponent implements OnInit {
  productImage: IProductImage;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productImage }) => {
      this.productImage = productImage;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
