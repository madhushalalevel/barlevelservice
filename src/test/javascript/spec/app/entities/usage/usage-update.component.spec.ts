import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BarlevelserviceTestModule } from '../../../test.module';
import { UsageUpdateComponent } from 'app/entities/usage/usage-update.component';
import { UsageService } from 'app/entities/usage/usage.service';
import { Usage } from 'app/shared/model/usage.model';

describe('Component Tests', () => {
  describe('Usage Management Update Component', () => {
    let comp: UsageUpdateComponent;
    let fixture: ComponentFixture<UsageUpdateComponent>;
    let service: UsageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarlevelserviceTestModule],
        declarations: [UsageUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UsageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Usage(123);
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
        const entity = new Usage();
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
