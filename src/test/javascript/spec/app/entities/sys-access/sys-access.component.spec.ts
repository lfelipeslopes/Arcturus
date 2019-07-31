/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArcturusTestModule } from '../../../test.module';
import { SysAccessComponent } from 'app/entities/sys-access/sys-access.component';
import { SysAccessService } from 'app/entities/sys-access/sys-access.service';
import { SysAccess } from 'app/shared/model/sys-access.model';

describe('Component Tests', () => {
  describe('SysAccess Management Component', () => {
    let comp: SysAccessComponent;
    let fixture: ComponentFixture<SysAccessComponent>;
    let service: SysAccessService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysAccessComponent],
        providers: []
      })
        .overrideTemplate(SysAccessComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysAccessComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysAccessService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SysAccess(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sysAccesses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
