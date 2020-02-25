import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
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
  usage: IUsage;
  isSaving: boolean;

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

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ usage }) => {
      this.updateForm(usage);
      this.usage = usage;
    });
  }

  updateForm(usage: IUsage) {
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const usage = this.createFromForm();
    if (usage.id !== undefined) {
      this.subscribeToSaveResponse(this.usageService.update(usage));
    } else {
      this.subscribeToSaveResponse(this.usageService.create(usage));
    }
  }

  private createFromForm(): IUsage {
    const entity = {
      ...new Usage(),
      id: this.editForm.get(['id']).value,
      usageId: this.editForm.get(['usageId']).value,
      productID: this.editForm.get(['productID']).value,
      branchID: this.editForm.get(['branchID']).value,
      zoneID: this.editForm.get(['zoneID']).value,
      shelfID: this.editForm.get(['shelfID']).value,
      usage: this.editForm.get(['usage']).value,
      datetime: this.editForm.get(['datetime']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsage>>) {
    result.subscribe((res: HttpResponse<IUsage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
