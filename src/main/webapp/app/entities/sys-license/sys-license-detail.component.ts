import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysLicense } from 'app/shared/model/sys-license.model';

@Component({
  selector: 'jhi-sys-license-detail',
  templateUrl: './sys-license-detail.component.html'
})
export class SysLicenseDetailComponent implements OnInit {
  sysLicense: ISysLicense;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysLicense }) => {
      this.sysLicense = sysLicense;
    });
  }

  previousState() {
    window.history.back();
  }
}
