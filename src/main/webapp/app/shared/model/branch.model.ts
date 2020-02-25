export interface IBranch {
  id?: number;
  branchID?: number;
  name?: string;
  discription?: string;
  tenantId?: string;
  addressId?: number;
  companyName?: string;
  companyId?: number;
  inventoryId?: number;
}

export class Branch implements IBranch {
  constructor(
    public id?: number,
    public branchID?: number,
    public name?: string,
    public discription?: string,
    public tenantId?: string,
    public addressId?: number,
    public companyName?: string,
    public companyId?: number,
    public inventoryId?: number
  ) {}
}
