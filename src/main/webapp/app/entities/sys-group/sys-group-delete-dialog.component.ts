import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysGroup } from 'app/shared/model/sys-group.model';
import { SysGroupService } from './sys-group.service';

@Component({
  selector: 'jhi-sys-group-delete-dialog',
  templateUrl: './sys-group-delete-dialog.component.html'
})
export class SysGroupDeleteDialogComponent {
  sysGroup: ISysGroup;

  constructor(protected sysGroupService: SysGroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysGroupListModification',
        content: 'Deleted an sysGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-group-delete-popup',
  template: ''
})
export class SysGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysGroup = sysGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-group', { outlets: { popup: null } }]);
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
