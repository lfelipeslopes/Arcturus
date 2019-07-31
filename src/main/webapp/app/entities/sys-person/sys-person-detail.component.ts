import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysPerson } from 'app/shared/model/sys-person.model';

@Component({
  selector: 'jhi-sys-person-detail',
  templateUrl: './sys-person-detail.component.html'
})
export class SysPersonDetailComponent implements OnInit {
  sysPerson: ISysPerson;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysPerson }) => {
      this.sysPerson = sysPerson;
    });
  }

  previousState() {
    window.history.back();
  }
}
