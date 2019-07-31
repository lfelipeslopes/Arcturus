import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysUser } from 'app/shared/model/sys-user.model';
import { SysUserService } from './sys-user.service';

@Component({
  selector: 'jhi-sys-user-delete-dialog',
  templateUrl: './sys-user-delete-dialog.component.html'
})
export class SysUserDeleteDialogComponent {
  sysUser: ISysUser;

  constructor(protected sysUserService: SysUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysUserService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysUserListModification',
        content: 'Deleted an sysUser'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-user-delete-popup',
  template: ''
})
export class SysUserDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysUser }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysUser = sysUser;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-user', { outlets: { popup: null } }]);
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
