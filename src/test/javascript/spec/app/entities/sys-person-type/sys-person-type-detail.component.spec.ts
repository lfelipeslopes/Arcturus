/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonTypeDetailComponent } from 'app/entities/sys-person-type/sys-person-type-detail.component';
import { SysPersonType } from 'app/shared/model/sys-person-type.model';

describe('Component Tests', () => {
  describe('SysPersonType Management Detail Component', () => {
    let comp: SysPersonTypeDetailComponent;
    let fixture: ComponentFixture<SysPersonTypeDetailComponent>;
    const route = ({ data: of({ sysPersonType: new SysPersonType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysPersonTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysPersonTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysPersonType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
