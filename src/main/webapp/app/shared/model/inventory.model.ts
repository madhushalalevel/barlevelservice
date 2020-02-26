export interface IInventory {
  id?: number;
  tennentID?: number;
  currentStockCount?: number;
  productId?: number;
  inventoryStockId?: number;
  branchId?: number;
  zoneId?: number;
  shelfId?: number;
}

export class Inventory implements IInventory {
  constructor(
    public id?: number,
    public tennentID?: number,
    public currentStockCount?: number,
    public productId?: number,
    public inventoryStockId?: number,
    public branchId?: number,
    public zoneId?: number,
    public shelfId?: number
  ) {}
}
