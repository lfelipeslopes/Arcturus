/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ArcturusTestModule } from '../../../test.module';
import { SysIpGroupItemComponent } from 'app/entities/sys-ip-group-item/sys-ip-group-item.component';
import { SysIpGroupItemService } from 'app/entities/sys-ip-group-item/sys-ip-group-item.service';
import { SysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';

describe('Component Tests', () => {
  describe('SysIpGroupItem Management Component', () => {
    let comp: SysIpGroupItemComponent;
    let fixture: ComponentFixture<SysIpGroupItemComponent>;
    let service: SysIpGroupItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysIpGroupItemComponent],
        providers: []
      })
        .overrideTemplate(SysIpGroupItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysIpGroupItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysIpGroupItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SysIpGroupItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sysIpGroupItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
