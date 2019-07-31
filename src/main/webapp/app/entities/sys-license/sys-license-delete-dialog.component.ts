import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysLicense } from 'app/shared/model/sys-license.model';
import { SysLicenseService } from './sys-license.service';

@Component({
  selector: 'jhi-sys-license-delete-dialog',
  templateUrl: './sys-license-delete-dialog.component.html'
})
export class SysLicenseDeleteDialogComponent {
  sysLicense: ISysLicense;

  constructor(
    protected sysLicenseService: SysLicenseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysLicenseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysLicenseListModification',
        content: 'Deleted an sysLicense'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-license-delete-popup',
  template: ''
})
export class SysLicenseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysLicense }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysLicenseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysLicense = sysLicense;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-license', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-license', { outlets: { popup: null } }]);
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
