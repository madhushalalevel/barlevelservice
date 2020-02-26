import { Moment } from 'moment';

export interface IUsage {
  id?: number;
  usageId?: number;
  productID?: number;
  branchID?: number;
  zoneID?: number;
  shelfID?: number;
  usage?: number;
  datetime?: Moment;
}

export class Usage implements IUsage {
  constructor(
    public id?: number,
    public usageId?: number,
    public productID?: number,
    public branchID?: number,
    public zoneID?: number,
    public shelfID?: number,
    public usage?: number,
    public datetime?: Moment
  ) {}
}
