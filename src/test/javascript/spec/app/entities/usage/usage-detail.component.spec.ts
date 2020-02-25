/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BarLevelServiceTestModule } from '../../../test.module';
import { UsageDetailComponent } from 'app/entities/usage/usage-detail.component';
import { Usage } from 'app/shared/model/usage.model';

describe('Component Tests', () => {
  describe('Usage Management Detail Component', () => {
    let comp: UsageDetailComponent;
    let fixture: ComponentFixture<UsageDetailComponent>;
    const route = ({ data: of({ usage: new Usage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BarLevelServiceTestModule],
        declarations: [UsageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UsageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
