/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonUpdateComponent } from 'app/entities/sys-person/sys-person-update.component';
import { SysPersonService } from 'app/entities/sys-person/sys-person.service';
import { SysPerson } from 'app/shared/model/sys-person.model';

describe('Component Tests', () => {
  describe('SysPerson Management Update Component', () => {
    let comp: SysPersonUpdateComponent;
    let fixture: ComponentFixture<SysPersonUpdateComponent>;
    let service: SysPersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysPersonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysPersonUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysPersonService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysPerson(123);
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
        const entity = new SysPerson();
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
