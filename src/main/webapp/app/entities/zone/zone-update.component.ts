import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IZone, Zone } from 'app/shared/model/zone.model';
import { ZoneService } from './zone.service';
import { IBranch } from 'app/shared/model/branch.model';
import { BranchService } from 'app/entities/branch/branch.service';

@Component({
  selector: 'jhi-zone-update',
  templateUrl: './zone-update.component.html'
})
export class ZoneUpdateComponent implements OnInit {
  isSaving = false;
  branches: IBranch[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    discription: [],
    tenantId: [],
    branchId: []
  });

  constructor(
    protected zoneService: ZoneService,
    protected branchService: BranchService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zone }) => {
      this.updateForm(zone);

      this.branchService.query().subscribe((res: HttpResponse<IBranch[]>) => (this.branches = res.body || []));
    });
  }

  updateForm(zone: IZone): void {
    this.editForm.patchValue({
      id: zone.id,
      name: zone.name,
      discription: zone.discription,
      tenantId: zone.tenantId,
      branchId: zone.branchId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const zone = this.createFromForm();
    if (zone.id !== undefined) {
      this.subscribeToSaveResponse(this.zoneService.update(zone));
    } else {
      this.subscribeToSaveResponse(this.zoneService.create(zone));
    }
  }

  private createFromForm(): IZone {
    return {
      ...new Zone(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      discription: this.editForm.get(['discription'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      branchId: this.editForm.get(['branchId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZone>>): void {
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

  trackById(index: number, item: IBranch): any {
    return item.id;
  }
}
