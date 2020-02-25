import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BarLevelServiceTestModule } from '../../../test.module';
import { ProductImageDetailComponent } from 'app/entities/product-image/product-image-detail.component';
import { ProductImage } from 'app/shared/model/product-image.model';

describe('Component Tests', () => {
  describe('ProductImage Management Detail Component', () => {
    let comp: ProductImageDetailComponent;
    let fixture: ComponentFixture<ProductImageDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ productImage: new ProductImage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [ProductImageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductImageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductImageDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load productImage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productImage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
