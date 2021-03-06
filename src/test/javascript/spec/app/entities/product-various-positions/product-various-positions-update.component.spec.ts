import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BarlevelserviceTestModule } from '../../../test.module';
import { ProductVariousPositionsUpdateComponent } from 'app/entities/product-various-positions/product-various-positions-update.component';
import { ProductVariousPositionsService } from 'app/entities/product-various-positions/product-various-positions.service';
import { ProductVariousPositions } from 'app/shared/model/product-various-positions.model';

describe('Component Tests', () => {
  describe('ProductVariousPositions Management Update Component', () => {
    let comp: ProductVariousPositionsUpdateComponent;
    let fixture: ComponentFixture<ProductVariousPositionsUpdateComponent>;
    let service: ProductVariousPositionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarlevelserviceTestModule],
        declarations: [ProductVariousPositionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductVariousPositionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductVariousPositionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductVariousPositionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductVariousPositions(123);
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
        const entity = new ProductVariousPositions();
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
