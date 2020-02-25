import { Moment } from 'moment';

export interface IProductPositions {
  id?: number;
  position?: number;
  updatedTime?: Moment;
  productName?: string;
  productId?: number;
}

export class ProductPositions implements IProductPositions {
  constructor(
    public id?: number,
    public position?: number,
    public updatedTime?: Moment,
    public productName?: string,
    public productId?: number
  ) {}
}
