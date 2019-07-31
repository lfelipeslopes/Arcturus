import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISysAccess } from 'app/shared/model/sys-access.model';
import { AccountService } from 'app/core';
import { SysAccessService } from './sys-access.service';

@Component({
  selector: 'jhi-sys-access',
  templateUrl: './sys-access.component.html'
})
export class SysAccessComponent implements OnInit, OnDestroy {
  sysAccesses: ISysAccess[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sysAccessService: SysAccessService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sysAccessService
      .query()
      .pipe(
        filter((res: HttpResponse<ISysAccess[]>) => res.ok),
        map((res: HttpResponse<ISysAccess[]>) => res.body)
      )
      .subscribe(
        (res: ISysAccess[]) => {
          this.sysAccesses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSysAccesses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISysAccess) {
    return item.id;
  }

  registerChangeInSysAccesses() {
    this.eventSubscriber = this.eventManager.subscribe('sysAccessListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
