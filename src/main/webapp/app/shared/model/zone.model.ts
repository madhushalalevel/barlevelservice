import { IInventory } from 'app/shared/model/inventory.model';

export interface IZone {
  id?: number;
  name?: string;
  discription?: string;
  tenantId?: string;
  inventories?: IInventory[];
  branchName?: string;
  branchId?: number;
}

export class Zone implements IZone {
  constructor(
    public id?: number,
    public name?: string,
    public discription?: string,
    public tenantId?: string,
    public inventories?: IInventory[],
    public branchName?: string,
    public branchId?: number
  ) {}
}
