/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonDeleteDialogComponent } from 'app/entities/sys-person/sys-person-delete-dialog.component';
import { SysPersonService } from 'app/entities/sys-person/sys-person.service';

describe('Component Tests', () => {
  describe('SysPerson Management Delete Component', () => {
    let comp: SysPersonDeleteDialogComponent;
    let fixture: ComponentFixture<SysPersonDeleteDialogComponent>;
    let service: SysPersonService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonDeleteDialogComponent]
      })
        .overrideTemplate(SysPersonDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysPersonDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysPersonService);
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
