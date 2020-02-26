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

@Component({
  selector: 'jhi-shelf-update',
  templateUrl: './shelf-update.component.html'
})
export class ShelfUpdateComponent implements OnInit {
  isSaving = false;
  zones: IZone[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    discription: [],
    tenantId: [],
    zoneId: []
  });

  constructor(
    protected shelfService: ShelfService,
    protected zoneService: ZoneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shelf }) => {
      this.updateForm(shelf);

      this.zoneService.query().subscribe((res: HttpResponse<IZone[]>) => (this.zones = res.body || []));
    });
  }

  updateForm(shelf: IShelf): void {
    this.editForm.patchValue({
      id: shelf.id,
      name: shelf.name,
      discription: shelf.discription,
      tenantId: shelf.tenantId,
      zoneId: shelf.zoneId
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
      name: this.editForm.get(['name'])!.value,
      discription: this.editForm.get(['discription'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      zoneId: this.editForm.get(['zoneId'])!.value
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

  trackById(index: number, item: IZone): any {
    return item.id;
  }
}
