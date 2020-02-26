import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.BarlevelserviceProductModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.BarlevelserviceCompanyModule)
      },
      {
        path: 'branch',
        loadChildren: () => import('./branch/branch.module').then(m => m.BarlevelserviceBranchModule)
      },
      {
        path: 'zone',
        loadChildren: () => import('./zone/zone.module').then(m => m.BarlevelserviceZoneModule)
      },
      {
        path: 'shelf',
        loadChildren: () => import('./shelf/shelf.module').then(m => m.BarlevelserviceShelfModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.BarlevelserviceEmployeeModule)
      },
      {
        path: 'inventory',
        loadChildren: () => import('./inventory/inventory.module').then(m => m.BarlevelserviceInventoryModule)
      },
      {
        path: 'inventory-stock',
        loadChildren: () => import('./inventory-stock/inventory-stock.module').then(m => m.BarlevelserviceInventoryStockModule)
      },
      {
        path: 'usage',
        loadChildren: () => import('./usage/usage.module').then(m => m.BarlevelserviceUsageModule)
      },
      {
        path: 'product-various-positions',
        loadChildren: () =>
          import('./product-various-positions/product-various-positions.module').then(m => m.BarlevelserviceProductVariousPositionsModule)
      },
      {
        path: 'product-positions',
        loadChildren: () => import('./product-positions/product-positions.module').then(m => m.BarlevelserviceProductPositionsModule)
      },
      {
        path: 'product-image',
        loadChildren: () => import('./product-image/product-image.module').then(m => m.BarlevelserviceProductImageModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.BarlevelserviceAddressModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BarlevelserviceEntityModule {}
