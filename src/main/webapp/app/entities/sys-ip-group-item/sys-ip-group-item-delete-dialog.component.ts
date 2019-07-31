import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';
import { SysIpGroupItemService } from './sys-ip-group-item.service';

@Component({
  selector: 'jhi-sys-ip-group-item-delete-dialog',
  templateUrl: './sys-ip-group-item-delete-dialog.component.html'
})
export class SysIpGroupItemDeleteDialogComponent {
  sysIpGroupItem: ISysIpGroupItem;

  constructor(
    protected sysIpGroupItemService: SysIpGroupItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysIpGroupItemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysIpGroupItemListModification',
        content: 'Deleted an sysIpGroupItem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-ip-group-item-delete-popup',
  template: ''
})
export class SysIpGroupItemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysIpGroupItem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysIpGroupItemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysIpGroupItem = sysIpGroupItem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-ip-group-item', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-ip-group-item', { outlets: { popup: null } }]);
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
