import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBranch, Branch } from 'app/shared/model/branch.model';
import { BranchService } from './branch.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

type SelectableEntity = IAddress | ICompany;

@Component({
  selector: 'jhi-branch-update',
  templateUrl: './branch-update.component.html'
})
export class BranchUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    discription: [],
    tenantId: [],
    addressId: [],
    companyId: []
  });

  constructor(
    protected branchService: BranchService,
    protected addressService: AddressService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ branch }) => {
      this.updateForm(branch);

      this.addressService
        .query({ filter: 'branch-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!branch.addressId) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(branch.addressId)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(branch: IBranch): void {
    this.editForm.patchValue({
      id: branch.id,
      name: branch.name,
      discription: branch.discription,
      tenantId: branch.tenantId,
      addressId: branch.addressId,
      companyId: branch.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const branch = this.createFromForm();
    if (branch.id !== undefined) {
      this.subscribeToSaveResponse(this.branchService.update(branch));
    } else {
      this.subscribeToSaveResponse(this.branchService.create(branch));
    }
  }

  private createFromForm(): IBranch {
    return {
      ...new Branch(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      discription: this.editForm.get(['discription'])!.value,
      tenantId: this.editForm.get(['tenantId'])!.value,
      addressId: this.editForm.get(['addressId'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBranch>>): void {
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
