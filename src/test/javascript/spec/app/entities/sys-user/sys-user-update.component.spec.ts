/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysUserUpdateComponent } from 'app/entities/sys-user/sys-user-update.component';
import { SysUserService } from 'app/entities/sys-user/sys-user.service';
import { SysUser } from 'app/shared/model/sys-user.model';

describe('Component Tests', () => {
  describe('SysUser Management Update Component', () => {
    let comp: SysUserUpdateComponent;
    let fixture: ComponentFixture<SysUserUpdateComponent>;
    let service: SysUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysUser(123);
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
        const entity = new SysUser();
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
