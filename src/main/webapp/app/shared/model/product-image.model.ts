export interface IProductImage {
  id?: number;
  imageUrl?: string;
  imageContentType?: string;
  image?: any;
  productId?: number;
}

export class ProductImage implements IProductImage {
  constructor(
    public id?: number,
    public imageUrl?: string,
    public imageContentType?: string,
    public image?: any,
    public productId?: number
  ) {}
}
