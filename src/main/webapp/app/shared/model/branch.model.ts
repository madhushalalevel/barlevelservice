import { IInventory } from 'app/shared/model/inventory.model';

export interface IBranch {
  id?: number;
  name?: string;
  discription?: string;
  tenantId?: string;
  addressId?: number;
  inventories?: IInventory[];
  companyName?: string;
  companyId?: number;
}

export class Branch implements IBranch {
  constructor(
    public id?: number,
    public name?: string,
    public discription?: string,
    public tenantId?: string,
    public addressId?: number,
    public inventories?: IInventory[],
    public companyName?: string,
    public companyId?: number
  ) {}
}
