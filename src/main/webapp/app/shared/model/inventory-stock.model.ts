export interface IInventoryStock {
  id?: number;
  inventoryId?: number;
  productID?: number;
  stockCount?: number;
  datetime?: number;
}

export class InventoryStock implements IInventoryStock {
  constructor(
    public id?: number,
    public inventoryId?: number,
    public productID?: number,
    public stockCount?: number,
    public datetime?: number
  ) {}
}
