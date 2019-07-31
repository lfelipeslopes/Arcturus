import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISysPerson } from 'app/shared/model/sys-person.model';
import { AccountService } from 'app/core';
import { SysPersonService } from './sys-person.service';

@Component({
  selector: 'jhi-sys-person',
  templateUrl: './sys-person.component.html'
})
export class SysPersonComponent implements OnInit, OnDestroy {
  sysPeople: ISysPerson[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sysPersonService: SysPersonService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sysPersonService
      .query()
      .pipe(
        filter((res: HttpResponse<ISysPerson[]>) => res.ok),
        map((res: HttpResponse<ISysPerson[]>) => res.body)
      )
      .subscribe(
        (res: ISysPerson[]) => {
          this.sysPeople = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSysPeople();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISysPerson) {
    return item.id;
  }

  registerChangeInSysPeople() {
    this.eventSubscriber = this.eventManager.subscribe('sysPersonListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
