/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysAccessUpdateComponent } from 'app/entities/sys-access/sys-access-update.component';
import { SysAccessService } from 'app/entities/sys-access/sys-access.service';
import { SysAccess } from 'app/shared/model/sys-access.model';

describe('Component Tests', () => {
  describe('SysAccess Management Update Component', () => {
    let comp: SysAccessUpdateComponent;
    let fixture: ComponentFixture<SysAccessUpdateComponent>;
    let service: SysAccessService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysAccessUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysAccessUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysAccessUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysAccessService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysAccess(123);
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
        const entity = new SysAccess();
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
