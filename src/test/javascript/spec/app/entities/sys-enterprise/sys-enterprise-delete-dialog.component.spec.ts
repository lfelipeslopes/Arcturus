/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArcturusTestModule } from '../../../test.module';
import { SysEnterpriseDeleteDialogComponent } from 'app/entities/sys-enterprise/sys-enterprise-delete-dialog.component';
import { SysEnterpriseService } from 'app/entities/sys-enterprise/sys-enterprise.service';

describe('Component Tests', () => {
  describe('SysEnterprise Management Delete Component', () => {
    let comp: SysEnterpriseDeleteDialogComponent;
    let fixture: ComponentFixture<SysEnterpriseDeleteDialogComponent>;
    let service: SysEnterpriseService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysEnterpriseDeleteDialogComponent]
      })
        .overrideTemplate(SysEnterpriseDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysEnterpriseDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysEnterpriseService);
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
