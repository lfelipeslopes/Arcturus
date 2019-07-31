/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysModuleUpdateComponent } from 'app/entities/sys-module/sys-module-update.component';
import { SysModuleService } from 'app/entities/sys-module/sys-module.service';
import { SysModule } from 'app/shared/model/sys-module.model';

describe('Component Tests', () => {
  describe('SysModule Management Update Component', () => {
    let comp: SysModuleUpdateComponent;
    let fixture: ComponentFixture<SysModuleUpdateComponent>;
    let service: SysModuleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysModuleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysModuleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysModuleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysModuleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysModule(123);
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
        const entity = new SysModule();
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
