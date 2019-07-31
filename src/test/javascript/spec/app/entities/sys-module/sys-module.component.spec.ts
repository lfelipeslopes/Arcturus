/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArcturusTestModule } from '../../../test.module';
import { SysModuleComponent } from 'app/entities/sys-module/sys-module.component';
import { SysModuleService } from 'app/entities/sys-module/sys-module.service';
import { SysModule } from 'app/shared/model/sys-module.model';

describe('Component Tests', () => {
  describe('SysModule Management Component', () => {
    let comp: SysModuleComponent;
    let fixture: ComponentFixture<SysModuleComponent>;
    let service: SysModuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysModuleComponent],
        providers: []
      })
        .overrideTemplate(SysModuleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysModuleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysModuleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SysModule(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sysModules[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
