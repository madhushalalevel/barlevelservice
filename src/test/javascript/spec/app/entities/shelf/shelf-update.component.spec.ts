/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BarLevelServiceTestModule } from '../../../test.module';
import { ShelfUpdateComponent } from 'app/entities/shelf/shelf-update.component';
import { ShelfService } from 'app/entities/shelf/shelf.service';
import { Shelf } from 'app/shared/model/shelf.model';

describe('Component Tests', () => {
  describe('Shelf Management Update Component', () => {
    let comp: ShelfUpdateComponent;
    let fixture: ComponentFixture<ShelfUpdateComponent>;
    let service: ShelfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [ShelfUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ShelfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShelfUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShelfService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Shelf(123);
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
        const entity = new Shelf();
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
