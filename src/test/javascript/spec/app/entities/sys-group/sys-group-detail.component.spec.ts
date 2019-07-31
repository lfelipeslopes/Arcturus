/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysGroupDetailComponent } from 'app/entities/sys-group/sys-group-detail.component';
import { SysGroup } from 'app/shared/model/sys-group.model';

describe('Component Tests', () => {
  describe('SysGroup Management Detail Component', () => {
    let comp: SysGroupDetailComponent;
    let fixture: ComponentFixture<SysGroupDetailComponent>;
    const route = ({ data: of({ sysGroup: new SysGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
