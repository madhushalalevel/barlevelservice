import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBranch, Branch } from 'app/shared/model/branch.model';
import { BranchService } from './branch.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';
import { IInventory } from 'app/shared/model/inventory.model';
import { InventoryService } from 'app/entities/inventory';

@Component({
  selector: 'jhi-branch-update',
  templateUrl: './branch-update.component.html'
})
export class BranchUpdateComponent implements OnInit {
  branch: IBranch;
  isSaving: boolean;

  addresses: IAddress[];

  companies: ICompany[];

  inventories: IInventory[];

  editForm = this.fb.group({
    id: [],
    branchID: [],
    name: [],
    discription: [],
    tenantId: [],
    addressId: [],
    companyId: [],
    inventoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected branchService: BranchService,
    protected addressService: AddressService,
    protected companyService: CompanyService,
    protected inventoryService: InventoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ branch }) => {
      this.updateForm(branch);
      this.branch = branch;
    });
    this.addressService
      .query({ filter: 'branch-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IAddress[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAddress[]>) => response.body)
      )
      .subscribe(
        (res: IAddress[]) => {
          if (!this.branch.addressId) {
            this.addresses = res;
          } else {
            this.addressService
              .find(this.branch.addressId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IAddress>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IAddress>) => subResponse.body)
              )
              .subscribe(
                (subRes: IAddress) => (this.addresses = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.companyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompany[]>) => response.body)
      )
      .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.inventoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInventory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInventory[]>) => response.body)
      )
      .subscribe((res: IInventory[]) => (this.inventories = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(branch: IBranch) {
    this.editForm.patchValue({
      id: branch.id,
      branchID: branch.branchID,
      name: branch.name,
      discription: branch.discription,
      tenantId: branch.tenantId,
      addressId: branch.addressId,
      companyId: branch.companyId,
      inventoryId: branch.inventoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const branch = this.createFromForm();
    if (branch.id !== undefined) {
      this.subscribeToSaveResponse(this.branchService.update(branch));
    } else {
      this.subscribeToSaveResponse(this.branchService.create(branch));
    }
  }

  private createFromForm(): IBranch {
    const entity = {
      ...new Branch(),
      id: this.editForm.get(['id']).value,
      branchID: this.editForm.get(['branchID']).value,
      name: this.editForm.get(['name']).value,
      discription: this.editForm.get(['discription']).value,
      tenantId: this.editForm.get(['tenantId']).value,
      addressId: this.editForm.get(['addressId']).value,
      companyId: this.editForm.get(['companyId']).value,
      inventoryId: this.editForm.get(['inventoryId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranch>>) {
    result.subscribe((res: HttpResponse<IBranch>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAddressById(index: number, item: IAddress) {
    return item.id;
  }

  trackCompanyById(index: number, item: ICompany) {
    return item.id;
  }

  trackInventoryById(index: number, item: IInventory) {
    return item.id;
  }
}
