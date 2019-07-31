/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysLicenseUpdateComponent } from 'app/entities/sys-license/sys-license-update.component';
import { SysLicenseService } from 'app/entities/sys-license/sys-license.service';
import { SysLicense } from 'app/shared/model/sys-license.model';

describe('Component Tests', () => {
  describe('SysLicense Management Update Component', () => {
    let comp: SysLicenseUpdateComponent;
    let fixture: ComponentFixture<SysLicenseUpdateComponent>;
    let service: SysLicenseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysLicenseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysLicenseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysLicenseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysLicenseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysLicense(123);
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
        const entity = new SysLicense();
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
