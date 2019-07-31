import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysAccess } from 'app/shared/model/sys-access.model';
import { SysAccessService } from './sys-access.service';

@Component({
  selector: 'jhi-sys-access-delete-dialog',
  templateUrl: './sys-access-delete-dialog.component.html'
})
export class SysAccessDeleteDialogComponent {
  sysAccess: ISysAccess;

  constructor(protected sysAccessService: SysAccessService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysAccessService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysAccessListModification',
        content: 'Deleted an sysAccess'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-access-delete-popup',
  template: ''
})
export class SysAccessDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysAccess }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysAccessDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysAccess = sysAccess;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-access', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-access', { outlets: { popup: null } }]);
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
