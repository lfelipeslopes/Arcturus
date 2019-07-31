/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysGroupUpdateComponent } from 'app/entities/sys-group/sys-group-update.component';
import { SysGroupService } from 'app/entities/sys-group/sys-group.service';
import { SysGroup } from 'app/shared/model/sys-group.model';

describe('Component Tests', () => {
  describe('SysGroup Management Update Component', () => {
    let comp: SysGroupUpdateComponent;
    let fixture: ComponentFixture<SysGroupUpdateComponent>;
    let service: SysGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysGroup(123);
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
        const entity = new SysGroup();
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
