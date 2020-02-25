export interface IProductVariousPositions {
  id?: number;
  xAxis?: number;
  yAxis?: number;
  order?: number;
  productName?: string;
  productId?: number;
}

export class ProductVariousPositions implements IProductVariousPositions {
  constructor(
    public id?: number,
    public xAxis?: number,
    public yAxis?: number,
    public order?: number,
    public productName?: string,
    public productId?: number
  ) {}
}
