import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISysUser } from 'app/shared/model/sys-user.model';

@Component({
  selector: 'jhi-sys-user-detail',
  templateUrl: './sys-user-detail.component.html'
})
export class SysUserDetailComponent implements OnInit {
  sysUser: ISysUser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sysUser }) => {
      this.sysUser = sysUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
