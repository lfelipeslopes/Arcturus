import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';
import { AccountService } from 'app/core';
import { SysIpGroupService } from './sys-ip-group.service';

@Component({
  selector: 'jhi-sys-ip-group',
  templateUrl: './sys-ip-group.component.html'
})
export class SysIpGroupComponent implements OnInit, OnDestroy {
  sysIpGroups: ISysIpGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sysIpGroupService: SysIpGroupService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sysIpGroupService
      .query()
      .pipe(
        filter((res: HttpResponse<ISysIpGroup[]>) => res.ok),
        map((res: HttpResponse<ISysIpGroup[]>) => res.body)
      )
      .subscribe(
        (res: ISysIpGroup[]) => {
          this.sysIpGroups = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSysIpGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISysIpGroup) {
    return item.id;
  }

  registerChangeInSysIpGroups() {
    this.eventSubscriber = this.eventManager.subscribe('sysIpGroupListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
