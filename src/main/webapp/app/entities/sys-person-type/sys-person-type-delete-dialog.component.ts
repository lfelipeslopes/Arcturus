import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysPersonType } from 'app/shared/model/sys-person-type.model';
import { SysPersonTypeService } from './sys-person-type.service';

@Component({
  selector: 'jhi-sys-person-type-delete-dialog',
  templateUrl: './sys-person-type-delete-dialog.component.html'
})
export class SysPersonTypeDeleteDialogComponent {
  sysPersonType: ISysPersonType;

  constructor(
    protected sysPersonTypeService: SysPersonTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysPersonTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysPersonTypeListModification',
        content: 'Deleted an sysPersonType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-person-type-delete-popup',
  template: ''
})
export class SysPersonTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysPersonType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysPersonTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysPersonType = sysPersonType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-person-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-person-type', { outlets: { popup: null } }]);
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
