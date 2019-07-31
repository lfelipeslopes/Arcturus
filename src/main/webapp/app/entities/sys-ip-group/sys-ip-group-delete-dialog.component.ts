import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';
import { SysIpGroupService } from './sys-ip-group.service';

@Component({
  selector: 'jhi-sys-ip-group-delete-dialog',
  templateUrl: './sys-ip-group-delete-dialog.component.html'
})
export class SysIpGroupDeleteDialogComponent {
  sysIpGroup: ISysIpGroup;

  constructor(
    protected sysIpGroupService: SysIpGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysIpGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysIpGroupListModification',
        content: 'Deleted an sysIpGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-ip-group-delete-popup',
  template: ''
})
export class SysIpGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysIpGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysIpGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysIpGroup = sysIpGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-ip-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-ip-group', { outlets: { popup: null } }]);
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
