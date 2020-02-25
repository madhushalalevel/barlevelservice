/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BarLevelServiceTestModule } from '../../../test.module';
import { ProductPositionsUpdateComponent } from 'app/entities/product-positions/product-positions-update.component';
import { ProductPositionsService } from 'app/entities/product-positions/product-positions.service';
import { ProductPositions } from 'app/shared/model/product-positions.model';

describe('Component Tests', () => {
  describe('ProductPositions Management Update Component', () => {
    let comp: ProductPositionsUpdateComponent;
    let fixture: ComponentFixture<ProductPositionsUpdateComponent>;
    let service: ProductPositionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [ProductPositionsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductPositionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductPositionsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductPositionsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductPositions(123);
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
        const entity = new ProductPositions();
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
