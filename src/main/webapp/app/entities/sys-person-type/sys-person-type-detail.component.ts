import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysPersonType } from 'app/shared/model/sys-person-type.model';

@Component({
  selector: 'jhi-sys-person-type-detail',
  templateUrl: './sys-person-type-detail.component.html'
})
export class SysPersonTypeDetailComponent implements OnInit {
  sysPersonType: ISysPersonType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysPersonType }) => {
      this.sysPersonType = sysPersonType;
    });
  }

  previousState() {
    window.history.back();
  }
}
