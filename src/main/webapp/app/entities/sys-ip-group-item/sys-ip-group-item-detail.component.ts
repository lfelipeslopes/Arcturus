import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';

@Component({
  selector: 'jhi-sys-ip-group-item-detail',
  templateUrl: './sys-ip-group-item-detail.component.html'
})
export class SysIpGroupItemDetailComponent implements OnInit {
  sysIpGroupItem: ISysIpGroupItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysIpGroupItem }) => {
      this.sysIpGroupItem = sysIpGroupItem;
    });
  }

  previousState() {
    window.history.back();
  }
}
