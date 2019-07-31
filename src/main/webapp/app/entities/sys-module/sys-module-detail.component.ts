import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysModule } from 'app/shared/model/sys-module.model';

@Component({
  selector: 'jhi-sys-module-detail',
  templateUrl: './sys-module-detail.component.html'
})
export class SysModuleDetailComponent implements OnInit {
  sysModule: ISysModule;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysModule }) => {
      this.sysModule = sysModule;
    });
  }

  previousState() {
    window.history.back();
  }
}
