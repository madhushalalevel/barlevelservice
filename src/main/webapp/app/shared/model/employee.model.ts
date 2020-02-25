export interface IEmployee {
  id?: number;
  name?: string;
  phoneNumber?: number;
  email?: string;
  companyId?: number;
  addressId?: number;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public phoneNumber?: number,
    public email?: string,
    public companyId?: number,
    public addressId?: number
  ) {}
}
