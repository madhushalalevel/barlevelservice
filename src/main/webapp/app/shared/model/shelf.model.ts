import { IInventory } from 'app/shared/model/inventory.model';

export interface IShelf {
  id?: number;
  name?: string;
  discription?: string;
  tenantId?: string;
  inventories?: IInventory[];
  zoneName?: string;
  zoneId?: number;
}

export class Shelf implements IShelf {
  constructor(
    public id?: number,
    public name?: string,
    public discription?: string,
    public tenantId?: string,
    public inventories?: IInventory[],
    public zoneName?: string,
    public zoneId?: number
  ) {}
}
