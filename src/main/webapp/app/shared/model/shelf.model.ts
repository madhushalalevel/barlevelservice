export interface IShelf {
  id?: number;
  shelfID?: number;
  name?: string;
  discription?: string;
  tenantId?: string;
  zoneName?: string;
  zoneId?: number;
  inventoryId?: number;
}

export class Shelf implements IShelf {
  constructor(
    public id?: number,
    public shelfID?: number,
    public name?: string,
    public discription?: string,
    public tenantId?: string,
    public zoneName?: string,
    public zoneId?: number,
    public inventoryId?: number
  ) {}
}
