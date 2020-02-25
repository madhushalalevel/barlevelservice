/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarLevelServiceTestModule } from '../../../test.module';
import { ShelfDeleteDialogComponent } from 'app/entities/shelf/shelf-delete-dialog.component';
import { ShelfService } from 'app/entities/shelf/shelf.service';

describe('Component Tests', () => {
  describe('Shelf Management Delete Component', () => {
    let comp: ShelfDeleteDialogComponent;
    let fixture: ComponentFixture<ShelfDeleteDialogComponent>;
    let service: ShelfService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [ShelfDeleteDialogComponent]
      })
        .overrideTemplate(ShelfDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShelfDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShelfService);
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
