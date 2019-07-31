/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArcturusTestModule } from '../../../test.module';
import { SysLicenseComponent } from 'app/entities/sys-license/sys-license.component';
import { SysLicenseService } from 'app/entities/sys-license/sys-license.service';
import { SysLicense } from 'app/shared/model/sys-license.model';

describe('Component Tests', () => {
  describe('SysLicense Management Component', () => {
    let comp: SysLicenseComponent;
    let fixture: ComponentFixture<SysLicenseComponent>;
    let service: SysLicenseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysLicenseComponent],
        providers: []
      })
        .overrideTemplate(SysLicenseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysLicenseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysLicenseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SysLicense(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sysLicenses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
