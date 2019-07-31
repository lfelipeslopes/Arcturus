import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysAccess } from 'app/shared/model/sys-access.model';

@Component({
  selector: 'jhi-sys-access-detail',
  templateUrl: './sys-access-detail.component.html'
})
export class SysAccessDetailComponent implements OnInit {
  sysAccess: ISysAccess;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysAccess }) => {
      this.sysAccess = sysAccess;
    });
  }

  previousState() {
    window.history.back();
  }
}
