import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BarLevelServiceTestModule } from '../../../test.module';
import { InventoryStockUpdateComponent } from 'app/entities/inventory-stock/inventory-stock-update.component';
import { InventoryStockService } from 'app/entities/inventory-stock/inventory-stock.service';
import { InventoryStock } from 'app/shared/model/inventory-stock.model';

describe('Component Tests', () => {
  describe('InventoryStock Management Update Component', () => {
    let comp: InventoryStockUpdateComponent;
    let fixture: ComponentFixture<InventoryStockUpdateComponent>;
    let service: InventoryStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [InventoryStockUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventoryStockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryStockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryStockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryStock(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryStock();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
