import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';
import { SysEnterpriseService } from './sys-enterprise.service';

@Component({
  selector: 'jhi-sys-enterprise-delete-dialog',
  templateUrl: './sys-enterprise-delete-dialog.component.html'
})
export class SysEnterpriseDeleteDialogComponent {
  sysEnterprise: ISysEnterprise;

  constructor(
    protected sysEnterpriseService: SysEnterpriseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sysEnterpriseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sysEnterpriseListModification',
        content: 'Deleted an sysEnterprise'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sys-enterprise-delete-popup',
  template: ''
})
export class SysEnterpriseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysEnterprise }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SysEnterpriseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sysEnterprise = sysEnterprise;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sys-enterprise', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sys-enterprise', { outlets: { popup: null } }]);
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
