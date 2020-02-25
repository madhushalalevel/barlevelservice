import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
  company: ICompany;
  isSaving: boolean;

  inventories: IInventory[];

  editForm = this.fb.group({
    id: [],
    name: [],
    discription: [],
    tenantId: [],
    inventoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected companyService: CompanyService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);
      this.company = company;
    });
    this.inventoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInventory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInventory[]>) => response.body)
      )
      .subscribe((res: IInventory[]) => (this.inventories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(company: ICompany) {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      discription: company.discription,
      tenantId: company.tenantId,
      inventoryId: company.inventoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    const entity = {
      ...new Company(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      discription: this.editForm.get(['discription']).value,
      tenantId: this.editForm.get(['tenantId']).value,
      inventoryId: this.editForm.get(['inventoryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>) {
    result.subscribe((res: HttpResponse<ICompany>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackInventoryById(index: number, item: IInventory) {
    return item.id;
  }
}
