import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: './product/product.module#BarLevelServiceProductModule'
      },
      {
        path: 'company',
        loadChildren: './company/company.module#BarLevelServiceCompanyModule'
      },
      {
        path: 'branch',
        loadChildren: './branch/branch.module#BarLevelServiceBranchModule'
      },
      {
        path: 'zone',
        loadChildren: './zone/zone.module#BarLevelServiceZoneModule'
      },
      {
        path: 'shelf',
        loadChildren: './shelf/shelf.module#BarLevelServiceShelfModule'
      },
      {
        path: 'employee',
        loadChildren: './employee/employee.module#BarLevelServiceEmployeeModule'
      },
      {
        path: 'inventory',
        loadChildren: './inventory/inventory.module#BarLevelServiceInventoryModule'
      },
      {
        path: 'inventory-stock',
        loadChildren: './inventory-stock/inventory-stock.module#BarLevelServiceInventoryStockModule'
      },
      {
        path: 'usage',
        loadChildren: './usage/usage.module#BarLevelServiceUsageModule'
      },
      {
        path: 'product-various-positions',
        loadChildren: './product-various-positions/product-various-positions.module#BarLevelServiceProductVariousPositionsModule'
      },
      {
        path: 'product-positions',
        loadChildren: './product-positions/product-positions.module#BarLevelServiceProductPositionsModule'
      },
      {
        path: 'product-image',
        loadChildren: './product-image/product-image.module#BarLevelServiceProductImageModule'
      },
      {
        path: 'address',
        loadChildren: './address/address.module#BarLevelServiceAddressModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarLevelServiceEntityModule {}
