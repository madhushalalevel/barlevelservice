/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarLevelServiceTestModule } from '../../../test.module';
import { ProductVariousPositionsDeleteDialogComponent } from 'app/entities/product-various-positions/product-various-positions-delete-dialog.component';
import { ProductVariousPositionsService } from 'app/entities/product-various-positions/product-various-positions.service';

describe('Component Tests', () => {
  describe('ProductVariousPositions Management Delete Component', () => {
    let comp: ProductVariousPositionsDeleteDialogComponent;
    let fixture: ComponentFixture<ProductVariousPositionsDeleteDialogComponent>;
    let service: ProductVariousPositionsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [ProductVariousPositionsDeleteDialogComponent]
      })
        .overrideTemplate(ProductVariousPositionsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductVariousPositionsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductVariousPositionsService);
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
