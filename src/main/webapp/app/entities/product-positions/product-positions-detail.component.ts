import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductPositions } from 'app/shared/model/product-positions.model';

@Component({
  selector: 'jhi-product-positions-detail',
  templateUrl: './product-positions-detail.component.html'
})
export class ProductPositionsDetailComponent implements OnInit {
  productPositions: IProductPositions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPositions }) => (this.productPositions = productPositions));
  }

  previousState(): void {
    window.history.back();
  }
}
