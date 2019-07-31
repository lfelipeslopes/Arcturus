/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonDetailComponent } from 'app/entities/sys-person/sys-person-detail.component';
import { SysPerson } from 'app/shared/model/sys-person.model';

describe('Component Tests', () => {
  describe('SysPerson Management Detail Component', () => {
    let comp: SysPersonDetailComponent;
    let fixture: ComponentFixture<SysPersonDetailComponent>;
    const route = ({ data: of({ sysPerson: new SysPerson(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysPersonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysPersonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysPerson).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
