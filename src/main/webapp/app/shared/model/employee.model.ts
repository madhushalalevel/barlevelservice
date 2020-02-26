export interface IEmployee {
  id?: number;
  name?: string;
  phoneNumber?: number;
  email?: string;
  addressId?: number;
  companyId?: number;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public phoneNumber?: number,
    public email?: string,
    public addressId?: number,
    public companyId?: number
  ) {}
}
