/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArcturusTestModule } from '../../../test.module';
import { SysIpGroupComponent } from 'app/entities/sys-ip-group/sys-ip-group.component';
import { SysIpGroupService } from 'app/entities/sys-ip-group/sys-ip-group.service';
import { SysIpGroup } from 'app/shared/model/sys-ip-group.model';

describe('Component Tests', () => {
  describe('SysIpGroup Management Component', () => {
    let comp: SysIpGroupComponent;
    let fixture: ComponentFixture<SysIpGroupComponent>;
    let service: SysIpGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysIpGroupComponent],
        providers: []
      })
        .overrideTemplate(SysIpGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysIpGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysIpGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SysIpGroup(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sysIpGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
