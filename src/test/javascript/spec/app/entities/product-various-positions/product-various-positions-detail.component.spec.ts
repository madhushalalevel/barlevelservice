import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BarlevelserviceTestModule } from '../../../test.module';
import { ProductVariousPositionsDetailComponent } from 'app/entities/product-various-positions/product-various-positions-detail.component';
import { ProductVariousPositions } from 'app/shared/model/product-various-positions.model';

describe('Component Tests', () => {
  describe('ProductVariousPositions Management Detail Component', () => {
    let comp: ProductVariousPositionsDetailComponent;
    let fixture: ComponentFixture<ProductVariousPositionsDetailComponent>;
    const route = ({ data: of({ productVariousPositions: new ProductVariousPositions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarlevelserviceTestModule],
        declarations: [ProductVariousPositionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductVariousPositionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductVariousPositionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productVariousPositions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productVariousPositions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
