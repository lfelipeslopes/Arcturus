/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysAccessDetailComponent } from 'app/entities/sys-access/sys-access-detail.component';
import { SysAccess } from 'app/shared/model/sys-access.model';

describe('Component Tests', () => {
  describe('SysAccess Management Detail Component', () => {
    let comp: SysAccessDetailComponent;
    let fixture: ComponentFixture<SysAccessDetailComponent>;
    const route = ({ data: of({ sysAccess: new SysAccess(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysAccessDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysAccessDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysAccessDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysAccess).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
