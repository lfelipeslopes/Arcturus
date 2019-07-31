/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonTypeUpdateComponent } from 'app/entities/sys-person-type/sys-person-type-update.component';
import { SysPersonTypeService } from 'app/entities/sys-person-type/sys-person-type.service';
import { SysPersonType } from 'app/shared/model/sys-person-type.model';

describe('Component Tests', () => {
  describe('SysPersonType Management Update Component', () => {
    let comp: SysPersonTypeUpdateComponent;
    let fixture: ComponentFixture<SysPersonTypeUpdateComponent>;
    let service: SysPersonTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysPersonTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysPersonTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysPersonTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysPersonType(123);
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
        const entity = new SysPersonType();
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
