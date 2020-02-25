import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IShelf, Shelf } from 'app/shared/model/shelf.model';
import { ShelfService } from './shelf.service';
import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from 'app/entities/zone';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory';

@Component({
  selector: 'jhi-shelf-update',
  templateUrl: './shelf-update.component.html'
})
export class ShelfUpdateComponent implements OnInit {
  shelf: IShelf;
  isSaving: boolean;

  zones: IZone[];

  inventories: IInventory[];

  editForm = this.fb.group({
    id: [],
    shelfID: [],
    name: [],
    discription: [],
    tenantId: [],
    zoneId: [],
    inventoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected shelfService: ShelfService,
    protected zoneService: ZoneService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shelf }) => {
      this.updateForm(shelf);
      this.shelf = shelf;
    });
    this.zoneService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IZone[]>) => mayBeOk.ok),
        map((response: HttpResponse<IZone[]>) => response.body)
      )
      .subscribe((res: IZone[]) => (this.zones = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.inventoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInventory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInventory[]>) => response.body)
      )
      .subscribe((res: IInventory[]) => (this.inventories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(shelf: IShelf) {
    this.editForm.patchValue({
      id: shelf.id,
      shelfID: shelf.shelfID,
      name: shelf.name,
      discription: shelf.discription,
      tenantId: shelf.tenantId,
      zoneId: shelf.zoneId,
      inventoryId: shelf.inventoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shelf = this.createFromForm();
    if (shelf.id !== undefined) {
      this.subscribeToSaveResponse(this.shelfService.update(shelf));
    } else {
      this.subscribeToSaveResponse(this.shelfService.create(shelf));
    }
  }

  private createFromForm(): IShelf {
    const entity = {
      ...new Shelf(),
      id: this.editForm.get(['id']).value,
      shelfID: this.editForm.get(['shelfID']).value,
      name: this.editForm.get(['name']).value,
      discription: this.editForm.get(['discription']).value,
      tenantId: this.editForm.get(['tenantId']).value,
      zoneId: this.editForm.get(['zoneId']).value,
      inventoryId: this.editForm.get(['inventoryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShelf>>) {
    result.subscribe((res: HttpResponse<IShelf>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackZoneById(index: number, item: IZone) {
    return item.id;
  }

  trackInventoryById(index: number, item: IInventory) {
    return item.id;
  }
}
