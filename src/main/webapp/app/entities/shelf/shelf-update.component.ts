import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IShelf, Shelf } from 'app/shared/model/shelf.model';
import { ShelfService } from './shelf.service';
import { IZone } from 'app/shared/model/zone.model';
import { ZoneService } from 'app/entities/zone/zone.service';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory/inventory.service';

type SelectableEntity = IZone | IInventory;

@Component({
  selector: 'jhi-shelf-update',
  templateUrl: './shelf-update.component.html'
})
export class ShelfUpdateComponent implements OnInit {
  isSaving = false;
  zones: IZone[] = [];
  inventories: IInventory[] = [];

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
    protected shelfService: ShelfService,
    protected zoneService: ZoneService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shelf }) => {
      this.updateForm(shelf);

      this.zoneService.query().subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));

      this.inventoryService.query().subscribe((res: HttpResponse<IInventory[]>) => (this.inventories = res.body || []));
    });
  }

  updateForm(shelf: IShelf): void {
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shelf = this.createFromForm();
    if (shelf.id !== undefined) {
      this.subscribeToSaveResponse(this.shelfService.update(shelf));
    } else {
      this.subscribeToSaveResponse(this.shelfService.create(shelf));
    }
  }

  private createFromForm(): IShelf {
    return {
      ...new Shelf(),
      id: this.editForm.get(['id'])!.value,
      shelfID: this.editForm.get(['shelfID'])!.value,
      name: this.editForm.get(['name'])!.value,
      discription: this.editForm.get(['discription'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      zoneId: this.editForm.get(['zoneId'])!.value,
      inventoryId: this.editForm.get(['inventoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShelf>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
