export interface IZone {
  id?: number;
  zoneID?: number;
  name?: string;
  discription?: string;
  tenantId?: string;
  branchName?: string;
  branchId?: number;
  inventoryId?: number;
}

export class Zone implements IZone {
  constructor(
    public id?: number,
    public zoneID?: number,
    public name?: string,
    public discription?: string,
    public tenantId?: string,
    public branchName?: string,
    public branchId?: number,
    public inventoryId?: number
  ) {}
}
