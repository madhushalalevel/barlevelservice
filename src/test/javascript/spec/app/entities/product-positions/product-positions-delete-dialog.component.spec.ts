/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarLevelServiceTestModule } from '../../../test.module';
import { ProductPositionsDeleteDialogComponent } from 'app/entities/product-positions/product-positions-delete-dialog.component';
import { ProductPositionsService } from 'app/entities/product-positions/product-positions.service';

describe('Component Tests', () => {
  describe('ProductPositions Management Delete Component', () => {
    let comp: ProductPositionsDeleteDialogComponent;
    let fixture: ComponentFixture<ProductPositionsDeleteDialogComponent>;
    let service: ProductPositionsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [ProductPositionsDeleteDialogComponent]
      })
        .overrideTemplate(ProductPositionsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductPositionsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductPositionsService);
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
