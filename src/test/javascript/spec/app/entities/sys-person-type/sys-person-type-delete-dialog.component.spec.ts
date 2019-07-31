/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArcturusTestModule } from '../../../test.module';
import { SysPersonTypeDeleteDialogComponent } from 'app/entities/sys-person-type/sys-person-type-delete-dialog.component';
import { SysPersonTypeService } from 'app/entities/sys-person-type/sys-person-type.service';

describe('Component Tests', () => {
  describe('SysPersonType Management Delete Component', () => {
    let comp: SysPersonTypeDeleteDialogComponent;
    let fixture: ComponentFixture<SysPersonTypeDeleteDialogComponent>;
    let service: SysPersonTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysPersonTypeDeleteDialogComponent]
      })
        .overrideTemplate(SysPersonTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysPersonTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysPersonTypeService);
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
