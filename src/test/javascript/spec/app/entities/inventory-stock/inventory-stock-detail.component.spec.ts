/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BarLevelServiceTestModule } from '../../../test.module';
import { InventoryStockDetailComponent } from 'app/entities/inventory-stock/inventory-stock-detail.component';
import { InventoryStock } from 'app/shared/model/inventory-stock.model';

describe('Component Tests', () => {
  describe('InventoryStock Management Detail Component', () => {
    let comp: InventoryStockDetailComponent;
    let fixture: ComponentFixture<InventoryStockDetailComponent>;
    const route = ({ data: of({ inventoryStock: new InventoryStock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [InventoryStockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InventoryStockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryStockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryStock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
