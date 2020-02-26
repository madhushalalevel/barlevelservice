import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInventoryStock } from 'app/shared/model/inventory-stock.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InventoryStockService } from './inventory-stock.service';
import { InventoryStockDeleteDialogComponent } from './inventory-stock-delete-dialog.component';

@Component({
  selector: 'jhi-inventory-stock',
  templateUrl: './inventory-stock.component.html'
})
export class InventoryStockComponent implements OnInit, OnDestroy {
  inventoryStocks?: IInventoryStock[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected inventoryStockService: InventoryStockService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.inventoryStockService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IInventoryStock[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInInventoryStocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInventoryStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInventoryStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('inventoryStockListModification', () => this.loadPage());
  }

  delete(inventoryStock: IInventoryStock): void {
    const modalRef = this.modalService.open(InventoryStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inventoryStock = inventoryStock;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IInventoryStock[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/inventory-stock'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.inventoryStocks = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
