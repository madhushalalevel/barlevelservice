import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductVariousPositions } from 'app/shared/model/product-various-positions.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ProductVariousPositionsService } from './product-various-positions.service';
import { ProductVariousPositionsDeleteDialogComponent } from './product-various-positions-delete-dialog.component';

@Component({
  selector: 'jhi-product-various-positions',
  templateUrl: './product-various-positions.component.html'
})
export class ProductVariousPositionsComponent implements OnInit, OnDestroy {
  productVariousPositions?: IProductVariousPositions[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected productVariousPositionsService: ProductVariousPositionsService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.productVariousPositionsService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IProductVariousPositions[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInProductVariousPositions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductVariousPositions): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductVariousPositions(): void {
    this.eventSubscriber = this.eventManager.subscribe('productVariousPositionsListModification', () => this.loadPage());
  }

  delete(productVariousPositions: IProductVariousPositions): void {
    const modalRef = this.modalService.open(ProductVariousPositionsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productVariousPositions = productVariousPositions;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IProductVariousPositions[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/product-various-positions'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.productVariousPositions = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
