/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArcturusTestModule } from '../../../test.module';
import { SysIpGroupItemDeleteDialogComponent } from 'app/entities/sys-ip-group-item/sys-ip-group-item-delete-dialog.component';
import { SysIpGroupItemService } from 'app/entities/sys-ip-group-item/sys-ip-group-item.service';

describe('Component Tests', () => {
  describe('SysIpGroupItem Management Delete Component', () => {
    let comp: SysIpGroupItemDeleteDialogComponent;
    let fixture: ComponentFixture<SysIpGroupItemDeleteDialogComponent>;
    let service: SysIpGroupItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ArcturusTestModule],
        declarations: [SysIpGroupItemDeleteDialogComponent]
      })
        .overrideTemplate(SysIpGroupItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SysIpGroupItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SysIpGroupItemService);
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
