/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BarLevelServiceTestModule } from '../../../test.module';
import { InventoryUpdateComponent } from 'app/entities/inventory/inventory-update.component';
import { InventoryService } from 'app/entities/inventory/inventory.service';
import { Inventory } from 'app/shared/model/inventory.model';

describe('Component Tests', () => {
  describe('Inventory Management Update Component', () => {
    let comp: InventoryUpdateComponent;
    let fixture: ComponentFixture<InventoryUpdateComponent>;
    let service: InventoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [InventoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inventory(123);
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
        const entity = new Inventory();
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
