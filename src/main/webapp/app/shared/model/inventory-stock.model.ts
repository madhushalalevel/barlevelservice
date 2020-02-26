import { Moment } from 'moment';

export interface IInventoryStock {
  id?: number;
  stockCount?: number;
  datetime?: Moment;
  inventoryId?: number;
}

export class InventoryStock implements IInventoryStock {
  constructor(public id?: number, public stockCount?: number, public datetime?: Moment, public inventoryId?: number) {}
}
