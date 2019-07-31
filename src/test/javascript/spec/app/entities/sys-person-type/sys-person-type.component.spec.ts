/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonTypeComponent } from 'app/entities/sys-person-type/sys-person-type.component';
import { SysPersonTypeService } from 'app/entities/sys-person-type/sys-person-type.service';
import { SysPersonType } from 'app/shared/model/sys-person-type.model';

describe('Component Tests', () => {
  describe('SysPersonType Management Component', () => {
    let comp: SysPersonTypeComponent;
    let fixture: ComponentFixture<SysPersonTypeComponent>;
    let service: SysPersonTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonTypeComponent],
        providers: []
      })
        .overrideTemplate(SysPersonTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysPersonTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysPersonTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SysPersonType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sysPersonTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
