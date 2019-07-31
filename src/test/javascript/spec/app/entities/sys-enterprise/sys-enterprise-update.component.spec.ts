/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysEnterpriseUpdateComponent } from 'app/entities/sys-enterprise/sys-enterprise-update.component';
import { SysEnterpriseService } from 'app/entities/sys-enterprise/sys-enterprise.service';
import { SysEnterprise } from 'app/shared/model/sys-enterprise.model';

describe('Component Tests', () => {
  describe('SysEnterprise Management Update Component', () => {
    let comp: SysEnterpriseUpdateComponent;
    let fixture: ComponentFixture<SysEnterpriseUpdateComponent>;
    let service: SysEnterpriseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysEnterpriseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysEnterpriseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysEnterpriseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysEnterpriseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysEnterprise(123);
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
        const entity = new SysEnterprise();
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
