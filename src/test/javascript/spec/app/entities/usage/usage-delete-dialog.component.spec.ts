/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarLevelServiceTestModule } from '../../../test.module';
import { UsageDeleteDialogComponent } from 'app/entities/usage/usage-delete-dialog.component';
import { UsageService } from 'app/entities/usage/usage.service';

describe('Component Tests', () => {
  describe('Usage Management Delete Component', () => {
    let comp: UsageDeleteDialogComponent;
    let fixture: ComponentFixture<UsageDeleteDialogComponent>;
    let service: UsageService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [UsageDeleteDialogComponent]
      })
        .overrideTemplate(UsageDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsageDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsageService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
