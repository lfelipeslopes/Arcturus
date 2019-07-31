import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysModule } from 'app/shared/model/sys-module.model';
import { SysModuleService } from './sys-module.service';

@Component({
  selector: 'jhi-sys-module-delete-dialog',
  templateUrl: './sys-module-delete-dialog.component.html'
})
export class SysModuleDeleteDialogComponent {
  sysModule: ISysModule;

  constructor(protected sysModuleService: SysModuleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysModuleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysModuleListModification',
        content: 'Deleted an sysModule'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-module-delete-popup',
  template: ''
})
export class SysModuleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysModule }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysModuleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysModule = sysModule;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-module', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-module', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
