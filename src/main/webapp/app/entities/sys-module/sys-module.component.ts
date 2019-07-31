import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISysModule } from 'app/shared/model/sys-module.model';
import { AccountService } from 'app/core';
import { SysModuleService } from './sys-module.service';

@Component({
  selector: 'jhi-sys-module',
  templateUrl: './sys-module.component.html'
})
export class SysModuleComponent implements OnInit, OnDestroy {
  sysModules: ISysModule[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected sysModuleService: SysModuleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.sysModuleService
      .query()
      .pipe(
        filter((res: HttpResponse<ISysModule[]>) => res.ok),
        map((res: HttpResponse<ISysModule[]>) => res.body)
      )
      .subscribe(
        (res: ISysModule[]) => {
          this.sysModules = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSysModules();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISysModule) {
    return item.id;
  }

  registerChangeInSysModules() {
    this.eventSubscriber = this.eventManager.subscribe('sysModuleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
