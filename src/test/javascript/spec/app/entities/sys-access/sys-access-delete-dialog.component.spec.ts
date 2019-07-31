/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArcturusTestModule } from '../../../test.module';
import { SysAccessDeleteDialogComponent } from 'app/entities/sys-access/sys-access-delete-dialog.component';
import { SysAccessService } from 'app/entities/sys-access/sys-access.service';

describe('Component Tests', () => {
  describe('SysAccess Management Delete Component', () => {
    let comp: SysAccessDeleteDialogComponent;
    let fixture: ComponentFixture<SysAccessDeleteDialogComponent>;
    let service: SysAccessService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysAccessDeleteDialogComponent]
      })
        .overrideTemplate(SysAccessDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysAccessDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysAccessService);
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
