import { IEmployee } from 'app/shared/model/employee.model';

export interface ICompany {
  id?: number;
  name?: string;
  discription?: string;
  tenantId?: string;
  employees?: IEmployee[];
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public discription?: string,
    public tenantId?: string,
    public employees?: IEmployee[]
  ) {}
}
