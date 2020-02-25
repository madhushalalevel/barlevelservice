import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductVariousPositions } from 'app/shared/model/product-various-positions.model';

@Component({
  selector: 'jhi-product-various-positions-detail',
  templateUrl: './product-various-positions-detail.component.html'
})
export class ProductVariousPositionsDetailComponent implements OnInit {
  productVariousPositions: IProductVariousPositions;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productVariousPositions }) => {
      this.productVariousPositions = productVariousPositions;
    });
  }

  previousState() {
    window.history.back();
  }
}
