/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysIpGroupUpdateComponent } from 'app/entities/sys-ip-group/sys-ip-group-update.component';
import { SysIpGroupService } from 'app/entities/sys-ip-group/sys-ip-group.service';
import { SysIpGroup } from 'app/shared/model/sys-ip-group.model';

describe('Component Tests', () => {
  describe('SysIpGroup Management Update Component', () => {
    let comp: SysIpGroupUpdateComponent;
    let fixture: ComponentFixture<SysIpGroupUpdateComponent>;
    let service: SysIpGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysIpGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysIpGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysIpGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysIpGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysIpGroup(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysIpGroup();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
