/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArcturusTestModule } from '../../../test.module';
import { SysLicenseDeleteDialogComponent } from 'app/entities/sys-license/sys-license-delete-dialog.component';
import { SysLicenseService } from 'app/entities/sys-license/sys-license.service';

describe('Component Tests', () => {
  describe('SysLicense Management Delete Component', () => {
    let comp: SysLicenseDeleteDialogComponent;
    let fixture: ComponentFixture<SysLicenseDeleteDialogComponent>;
    let service: SysLicenseService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysLicenseDeleteDialogComponent]
      })
        .overrideTemplate(SysLicenseDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysLicenseDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysLicenseService);
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
