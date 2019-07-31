/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArcturusTestModule } from '../../../test.module';
import { SysModuleDeleteDialogComponent } from 'app/entities/sys-module/sys-module-delete-dialog.component';
import { SysModuleService } from 'app/entities/sys-module/sys-module.service';

describe('Component Tests', () => {
  describe('SysModule Management Delete Component', () => {
    let comp: SysModuleDeleteDialogComponent;
    let fixture: ComponentFixture<SysModuleDeleteDialogComponent>;
    let service: SysModuleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysModuleDeleteDialogComponent]
      })
        .overrideTemplate(SysModuleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysModuleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysModuleService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
