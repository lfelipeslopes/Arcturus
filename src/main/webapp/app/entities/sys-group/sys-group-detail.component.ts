import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysGroup } from 'app/shared/model/sys-group.model';

@Component({
  selector: 'jhi-sys-group-detail',
  templateUrl: './sys-group-detail.component.html'
})
export class SysGroupDetailComponent implements OnInit {
  sysGroup: ISysGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysGroup }) => {
      this.sysGroup = sysGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
