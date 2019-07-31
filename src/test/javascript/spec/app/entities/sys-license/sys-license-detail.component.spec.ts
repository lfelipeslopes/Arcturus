/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysLicenseDetailComponent } from 'app/entities/sys-license/sys-license-detail.component';
import { SysLicense } from 'app/shared/model/sys-license.model';

describe('Component Tests', () => {
  describe('SysLicense Management Detail Component', () => {
    let comp: SysLicenseDetailComponent;
    let fixture: ComponentFixture<SysLicenseDetailComponent>;
    const route = ({ data: of({ sysLicense: new SysLicense(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysLicenseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysLicenseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysLicenseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysLicense).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
