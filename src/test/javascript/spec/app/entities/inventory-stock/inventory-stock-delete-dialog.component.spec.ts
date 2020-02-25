/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarLevelServiceTestModule } from '../../../test.module';
import { InventoryStockDeleteDialogComponent } from 'app/entities/inventory-stock/inventory-stock-delete-dialog.component';
import { InventoryStockService } from 'app/entities/inventory-stock/inventory-stock.service';

describe('Component Tests', () => {
  describe('InventoryStock Management Delete Component', () => {
    let comp: InventoryStockDeleteDialogComponent;
    let fixture: ComponentFixture<InventoryStockDeleteDialogComponent>;
    let service: InventoryStockService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [InventoryStockDeleteDialogComponent]
      })
        .overrideTemplate(InventoryStockDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryStockDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryStockService);
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
