/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysUserDetailComponent } from 'app/entities/sys-user/sys-user-detail.component';
import { SysUser } from 'app/shared/model/sys-user.model';

describe('Component Tests', () => {
  describe('SysUser Management Detail Component', () => {
    let comp: SysUserDetailComponent;
    let fixture: ComponentFixture<SysUserDetailComponent>;
    const route = ({ data: of({ sysUser: new SysUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
