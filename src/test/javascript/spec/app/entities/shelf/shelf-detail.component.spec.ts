import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BarlevelserviceTestModule } from '../../../test.module';
import { ShelfDetailComponent } from 'app/entities/shelf/shelf-detail.component';
import { Shelf } from 'app/shared/model/shelf.model';

describe('Component Tests', () => {
  describe('Shelf Management Detail Component', () => {
    let comp: ShelfDetailComponent;
    let fixture: ComponentFixture<ShelfDetailComponent>;
    const route = ({ data: of({ shelf: new Shelf(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarlevelserviceTestModule],
        declarations: [ShelfDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ShelfDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShelfDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shelf on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shelf).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
