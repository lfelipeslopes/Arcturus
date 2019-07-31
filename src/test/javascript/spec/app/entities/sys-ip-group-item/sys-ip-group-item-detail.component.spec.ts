/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysIpGroupItemDetailComponent } from 'app/entities/sys-ip-group-item/sys-ip-group-item-detail.component';
import { SysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';

describe('Component Tests', () => {
  describe('SysIpGroupItem Management Detail Component', () => {
    let comp: SysIpGroupItemDetailComponent;
    let fixture: ComponentFixture<SysIpGroupItemDetailComponent>;
    const route = ({ data: of({ sysIpGroupItem: new SysIpGroupItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysIpGroupItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SysIpGroupItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysIpGroupItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sysIpGroupItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
