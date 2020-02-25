export interface IAddress {
  id?: number;
  streetAddress1?: string;
  streetAddress2?: string;
  city?: string;
  state?: string;
  country?: string;
  zipCode?: string;
  branchId?: number;
  employeeId?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public streetAddress1?: string,
    public streetAddress2?: string,
    public city?: string,
    public state?: string,
    public country?: string,
    public zipCode?: string,
    public branchId?: number,
    public employeeId?: number
  ) {}
}
