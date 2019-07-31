import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';
import { AccountService } from 'app/core';
import { SysIpGroupItemService } from './sys-ip-group-item.service';

@Component({
  selector: 'jhi-sys-ip-group-item',
  templateUrl: './sys-ip-group-item.component.html'
})
export class SysIpGroupItemComponent implements OnInit, OnDestroy {
  sysIpGroupItems: ISysIpGroupItem[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sysIpGroupItemService: SysIpGroupItemService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sysIpGroupItemService
      .query()
      .pipe(
        filter((res: HttpResponse<ISysIpGroupItem[]>) => res.ok),
        map((res: HttpResponse<ISysIpGroupItem[]>) => res.body)
      )
      .subscribe(
        (res: ISysIpGroupItem[]) => {
          this.sysIpGroupItems = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSysIpGroupItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISysIpGroupItem) {
    return item.id;
  }

  registerChangeInSysIpGroupItems() {
    this.eventSubscriber = this.eventManager.subscribe('sysIpGroupItemListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
