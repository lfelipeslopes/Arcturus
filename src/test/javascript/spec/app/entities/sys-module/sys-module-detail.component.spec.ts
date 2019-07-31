/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysModuleDetailComponent } from 'app/entities/sys-module/sys-module-detail.component';
import { SysModule } from 'app/shared/model/sys-module.model';

describe('Component Tests', () => {
  describe('SysModule Management Detail Component', () => {
    let comp: SysModuleDetailComponent;
    let fixture: ComponentFixture<SysModuleDetailComponent>;
    const route = ({ data: of({ sysModule: new SysModule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysModuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysModuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysModuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysModule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
