import { IProduct } from 'app/shared/model/product.model';
import { ICompany } from 'app/shared/model/company.model';
import { IBranch } from 'app/shared/model/branch.model';
import { IZone } from 'app/shared/model/zone.model';
import { IShelf } from 'app/shared/model/shelf.model';

export interface IInventory {
  id?: number;
  productID?: number;
  tennentID?: number;
  branchID?: number;
  zoneID?: number;
  shelfID?: number;
  currentStockCount?: number;
  employees?: IProduct[];
  employees?: ICompany[];
  employees?: IBranch[];
  employees?: IZone[];
  employees?: IShelf[];
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public productID?: number,
    public tennentID?: number,
    public branchID?: number,
    public zoneID?: number,
    public shelfID?: number,
    public currentStockCount?: number,
    public employees?: IProduct[],
    public employees?: ICompany[],
    public employees?: IBranch[],
    public employees?: IZone[],
    public employees?: IShelf[]
  ) {}
}
