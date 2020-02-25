import { IProductPositions } from 'app/shared/model/product-positions.model';

export const enum BarCodeType {
  PUC = 'PUC',
  EAN = 'EAN'
}

export interface IProduct {
  id?: number;
  productID?: number;
  name?: string;
  barCode?: string;
  quantity?: number;
  volume?: number;
  type?: string;
  subType?: string;
  price?: number;
  containerType?: string;
  barCodeType?: BarCodeType;
  tenantId?: string;
  productPositions?: IProductPositions[];
  productImageId?: number;
  inventoryId?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productID?: number,
    public name?: string,
    public barCode?: string,
    public quantity?: number,
    public volume?: number,
    public type?: string,
    public subType?: string,
    public price?: number,
    public containerType?: string,
    public barCodeType?: BarCodeType,
    public tenantId?: string,
    public productPositions?: IProductPositions[],
    public productImageId?: number,
    public inventoryId?: number
  ) {}
}
