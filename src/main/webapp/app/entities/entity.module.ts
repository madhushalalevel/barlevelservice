import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.BarLevelServiceProductModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.BarLevelServiceCompanyModule)
      },
      {
        path: 'branch',
        loadChildren: () => import('./branch/branch.module').then(m => m.BarLevelServiceBranchModule)
      },
      {
        path: 'zone',
        loadChildren: () => import('./zone/zone.module').then(m => m.BarLevelServiceZoneModule)
      },
      {
        path: 'shelf',
        loadChildren: () => import('./shelf/shelf.module').then(m => m.BarLevelServiceShelfModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.BarLevelServiceEmployeeModule)
      },
      {
        path: 'inventory',
        loadChildren: () => import('./inventory/inventory.module').then(m => m.BarLevelServiceInventoryModule)
      },
      {
        path: 'inventory-stock',
        loadChildren: () => import('./inventory-stock/inventory-stock.module').then(m => m.BarLevelServiceInventoryStockModule)
      },
      {
        path: 'usage',
        loadChildren: () => import('./usage/usage.module').then(m => m.BarLevelServiceUsageModule)
      },
      {
        path: 'product-various-positions',
        loadChildren: () =>
          import('./product-various-positions/product-various-positions.module').then(m => m.BarLevelServiceProductVariousPositionsModule)
      },
      {
        path: 'product-positions',
        loadChildren: () => import('./product-positions/product-positions.module').then(m => m.BarLevelServiceProductPositionsModule)
      },
      {
        path: 'product-image',
        loadChildren: () => import('./product-image/product-image.module').then(m => m.BarLevelServiceProductImageModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.BarLevelServiceAddressModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BarLevelServiceEntityModule {}
