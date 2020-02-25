import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IZone, Zone } from 'app/shared/model/zone.model';
import { ZoneService } from './zone.service';
import { IBranch } from 'app/shared/model/branch.model';
import { BranchService } from 'app/entities/branch';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory';

@Component({
  selector: 'jhi-zone-update',
  templateUrl: './zone-update.component.html'
})
export class ZoneUpdateComponent implements OnInit {
  zone: IZone;
  isSaving: boolean;

  branches: IBranch[];

  inventories: IInventory[];

  editForm = this.fb.group({
    id: [],
    zoneID: [],
    name: [],
    discription: [],
    tenantId: [],
    branchId: [],
    inventoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected zoneService: ZoneService,
    protected branchService: BranchService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ zone }) => {
      this.updateForm(zone);
      this.zone = zone;
    });
    this.branchService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBranch[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBranch[]>) => response.body)
      )
      .subscribe((res: IBranch[]) => (this.branches = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.inventoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInventory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInventory[]>) => response.body)
      )
      .subscribe((res: IInventory[]) => (this.inventories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(zone: IZone) {
    this.editForm.patchValue({
      id: zone.id,
      zoneID: zone.zoneID,
      name: zone.name,
      discription: zone.discription,
      tenantId: zone.tenantId,
      branchId: zone.branchId,
      inventoryId: zone.inventoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const zone = this.createFromForm();
    if (zone.id !== undefined) {
      this.subscribeToSaveResponse(this.zoneService.update(zone));
    } else {
      this.subscribeToSaveResponse(this.zoneService.create(zone));
    }
  }

  private createFromForm(): IZone {
    const entity = {
      ...new Zone(),
      id: this.editForm.get(['id']).value,
      zoneID: this.editForm.get(['zoneID']).value,
      name: this.editForm.get(['name']).value,
      discription: this.editForm.get(['discription']).value,
      tenantId: this.editForm.get(['tenantId']).value,
      branchId: this.editForm.get(['branchId']).value,
      inventoryId: this.editForm.get(['inventoryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZone>>) {
    result.subscribe((res: HttpResponse<IZone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackBranchById(index: number, item: IBranch) {
    return item.id;
  }

  trackInventoryById(index: number, item: IInventory) {
    return item.id;
  }
}
