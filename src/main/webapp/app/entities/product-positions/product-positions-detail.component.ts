import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductPositions } from 'app/shared/model/product-positions.model';

@Component({
  selector: 'jhi-product-positions-detail',
  templateUrl: './product-positions-detail.component.html'
})
export class ProductPositionsDetailComponent implements OnInit {
  productPositions: IProductPositions;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productPositions }) => {
      this.productPositions = productPositions;
    });
  }

  previousState() {
    window.history.back();
  }
}
