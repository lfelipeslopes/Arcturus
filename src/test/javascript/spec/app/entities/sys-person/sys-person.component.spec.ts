/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonComponent } from 'app/entities/sys-person/sys-person.component';
import { SysPersonService } from 'app/entities/sys-person/sys-person.service';
import { SysPerson } from 'app/shared/model/sys-person.model';

describe('Component Tests', () => {
  describe('SysPerson Management Component', () => {
    let comp: SysPersonComponent;
    let fixture: ComponentFixture<SysPersonComponent>;
    let service: SysPersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonComponent],
        providers: []
      })
        .overrideTemplate(SysPersonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysPersonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysPersonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SysPerson(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sysPeople[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
