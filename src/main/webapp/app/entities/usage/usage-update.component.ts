import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUsage, Usage } from 'app/shared/model/usage.model';
import { UsageService } from './usage.service';

@Component({
  selector: 'jhi-usage-update',
  templateUrl: './usage-update.component.html'
})
export class UsageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    usageId: [],
    productID: [],
    branchID: [],
    zoneID: [],
    shelfID: [],
    usage: [],
    datetime: []
  });

  constructor(protected usageService: UsageService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usage }) => {
      this.updateForm(usage);
    });
  }

  updateForm(usage: IUsage): void {
    this.editForm.patchValue({
      id: usage.id,
      usageId: usage.usageId,
      productID: usage.productID,
      branchID: usage.branchID,
      zoneID: usage.zoneID,
      shelfID: usage.shelfID,
      usage: usage.usage,
      datetime: usage.datetime
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usage = this.createFromForm();
    if (usage.id !== undefined) {
      this.subscribeToSaveResponse(this.usageService.update(usage));
    } else {
      this.subscribeToSaveResponse(this.usageService.create(usage));
    }
  }

  private createFromForm(): IUsage {
    return {
      ...new Usage(),
      id: this.editForm.get(['id'])!.value,
      usageId: this.editForm.get(['usageId'])!.value,
      productID: this.editForm.get(['productID'])!.value,
      branchID: this.editForm.get(['branchID'])!.value,
      zoneID: this.editForm.get(['zoneID'])!.value,
      shelfID: this.editForm.get(['shelfID'])!.value,
      usage: this.editForm.get(['usage'])!.value,
      datetime: this.editForm.get(['datetime'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsage>>): void {
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
}
