import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysPerson } from 'app/shared/model/sys-person.model';
import { SysPersonService } from './sys-person.service';

@Component({
  selector: 'jhi-sys-person-delete-dialog',
  templateUrl: './sys-person-delete-dialog.component.html'
})
export class SysPersonDeleteDialogComponent {
  sysPerson: ISysPerson;

  constructor(protected sysPersonService: SysPersonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysPersonService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysPersonListModification',
        content: 'Deleted an sysPerson'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-person-delete-popup',
  template: ''
})
export class SysPersonDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysPerson }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysPersonDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysPerson = sysPerson;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-person', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-person', { outlets: { popup: null } }]);
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
