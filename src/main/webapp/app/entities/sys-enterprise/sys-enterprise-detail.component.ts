import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';

@Component({
  selector: 'jhi-sys-enterprise-detail',
  templateUrl: './sys-enterprise-detail.component.html'
})
export class SysEnterpriseDetailComponent implements OnInit {
  sysEnterprise: ISysEnterprise;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysEnterprise }) => {
      this.sysEnterprise = sysEnterprise;
    });
  }

  previousState() {
    window.history.back();
  }
}
