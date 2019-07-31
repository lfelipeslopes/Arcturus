import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISysLicense } from 'app/shared/model/sys-license.model';
import { AccountService } from 'app/core';
import { SysLicenseService } from './sys-license.service';

@Component({
  selector: 'jhi-sys-license',
  templateUrl: './sys-license.component.html'
})
export class SysLicenseComponent implements OnInit, OnDestroy {
  sysLicenses: ISysLicense[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sysLicenseService: SysLicenseService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sysLicenseService
      .query()
      .pipe(
        filter((res: HttpResponse<ISysLicense[]>) => res.ok),
        map((res: HttpResponse<ISysLicense[]>) => res.body)
      )
      .subscribe(
        (res: ISysLicense[]) => {
          this.sysLicenses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSysLicenses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISysLicense) {
    return item.id;
  }

  registerChangeInSysLicenses() {
    this.eventSubscriber = this.eventManager.subscribe('sysLicenseListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
