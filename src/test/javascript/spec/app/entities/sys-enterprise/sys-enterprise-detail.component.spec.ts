/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysEnterpriseDetailComponent } from 'app/entities/sys-enterprise/sys-enterprise-detail.component';
import { SysEnterprise } from 'app/shared/model/sys-enterprise.model';

describe('Component Tests', () => {
  describe('SysEnterprise Management Detail Component', () => {
    let comp: SysEnterpriseDetailComponent;
    let fixture: ComponentFixture<SysEnterpriseDetailComponent>;
    const route = ({ data: of({ sysEnterprise: new SysEnterprise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysEnterpriseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysEnterpriseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysEnterpriseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysEnterprise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
