import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BarlevelserviceTestModule } from '../../../test.module';
import { ProductPositionsDetailComponent } from 'app/entities/product-positions/product-positions-detail.component';
import { ProductPositions } from 'app/shared/model/product-positions.model';

describe('Component Tests', () => {
  describe('ProductPositions Management Detail Component', () => {
    let comp: ProductPositionsDetailComponent;
    let fixture: ComponentFixture<ProductPositionsDetailComponent>;
    const route = ({ data: of({ productPositions: new ProductPositions(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarlevelserviceTestModule],
        declarations: [ProductPositionsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductPositionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductPositionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productPositions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productPositions).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
