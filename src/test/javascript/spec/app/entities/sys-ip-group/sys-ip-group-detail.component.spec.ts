/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysIpGroupDetailComponent } from 'app/entities/sys-ip-group/sys-ip-group-detail.component';
import { SysIpGroup } from 'app/shared/model/sys-ip-group.model';

describe('Component Tests', () => {
  describe('SysIpGroup Management Detail Component', () => {
    let comp: SysIpGroupDetailComponent;
    let fixture: ComponentFixture<SysIpGroupDetailComponent>;
    const route = ({ data: of({ sysIpGroup: new SysIpGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysIpGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysIpGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysIpGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysIpGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
