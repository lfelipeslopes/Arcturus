import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';

@Component({
  selector: 'jhi-sys-ip-group-detail',
  templateUrl: './sys-ip-group-detail.component.html'
})
export class SysIpGroupDetailComponent implements OnInit {
  sysIpGroup: ISysIpGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysIpGroup }) => {
      this.sysIpGroup = sysIpGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
