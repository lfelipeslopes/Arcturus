/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ArcturusTestModule } from '../../../test.module';
import { SysIpGroupItemUpdateComponent } from 'app/entities/sys-ip-group-item/sys-ip-group-item-update.component';
import { SysIpGroupItemService } from 'app/entities/sys-ip-group-item/sys-ip-group-item.service';
import { SysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';

describe('Component Tests', () => {
  describe('SysIpGroupItem Management Update Component', () => {
    let comp: SysIpGroupItemUpdateComponent;
    let fixture: ComponentFixture<SysIpGroupItemUpdateComponent>;
    let service: SysIpGroupItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysIpGroupItemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SysIpGroupItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SysIpGroupItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysIpGroupItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SysIpGroupItem(123);
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
        const entity = new SysIpGroupItem();
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
