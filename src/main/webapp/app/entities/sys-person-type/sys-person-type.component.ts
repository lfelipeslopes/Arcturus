import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISysPersonType } from 'app/shared/model/sys-person-type.model';
import { AccountService } from 'app/core';
import { SysPersonTypeService } from './sys-person-type.service';

@Component({
  selector: 'jhi-sys-person-type',
  templateUrl: './sys-person-type.component.html'
})
export class SysPersonTypeComponent implements OnInit, OnDestroy {
  sysPersonTypes: ISysPersonType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sysPersonTypeService: SysPersonTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sysPersonTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<ISysPersonType[]>) => res.ok),
        map((res: HttpResponse<ISysPersonType[]>) => res.body)
      )
      .subscribe(
        (res: ISysPersonType[]) => {
          this.sysPersonTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSysPersonTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISysPersonType) {
    return item.id;
  }

  registerChangeInSysPersonTypes() {
    this.eventSubscriber = this.eventManager.subscribe('sysPersonTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
