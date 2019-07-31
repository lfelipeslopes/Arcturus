import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISysUser, SysUser } from 'app/shared/model/sys-user.model';
import { SysUserService } from './sys-user.service';
import { ISysPerson } from 'app/shared/model/sys-person.model';
import { SysPersonService } from 'app/entities/sys-person';
import { ISysGroup } from 'app/shared/model/sys-group.model';
import { SysGroupService } from 'app/entities/sys-group';
import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';
import { SysEnterpriseService } from 'app/entities/sys-enterprise';

@Component({
  selector: 'jhi-sys-user-update',
  templateUrl: './sys-user-update.component.html'
})
export class SysUserUpdateComponent implements OnInit {
  isSaving: boolean;

  syspeople: ISysPerson[];

  sysgroups: ISysGroup[];

  sysenterprises: ISysEnterprise[];

  editForm = this.fb.group({
    id: [],
    userId: [],
    status: [],
    name: [],
    sysPersonId: [],
    groupIds: [],
    enterpriseIds: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sysUserService: SysUserService,
    protected sysPersonService: SysPersonService,
    protected sysGroupService: SysGroupService,
    protected sysEnterpriseService: SysEnterpriseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysUser }) => {
      this.updateForm(sysUser);
    });
    this.sysPersonService
      .query({ filter: 'sysuser-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ISysPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysPerson[]>) => response.body)
      )
      .subscribe(
        (res: ISysPerson[]) => {
          if (!!this.editForm.get('sysPersonId').value) {
            this.syspeople = res;
          } else {
            this.sysPersonService
              .find(this.editForm.get('sysPersonId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ISysPerson>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ISysPerson>) => subResponse.body)
              )
              .subscribe(
                (subRes: ISysPerson) => (this.syspeople = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.sysGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysGroup[]>) => response.body)
      )
      .subscribe((res: ISysGroup[]) => (this.sysgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sysEnterpriseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysEnterprise[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysEnterprise[]>) => response.body)
      )
      .subscribe((res: ISysEnterprise[]) => (this.sysenterprises = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sysUser: ISysUser) {
    this.editForm.patchValue({
      id: sysUser.id,
      userId: sysUser.userId,
      status: sysUser.status,
      name: sysUser.name,
      sysPersonId: sysUser.sysPersonId,
      groupIds: sysUser.groupIds,
      enterpriseIds: sysUser.enterpriseIds
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysUser = this.createFromForm();
    if (sysUser.id !== undefined) {
      this.subscribeToSaveResponse(this.sysUserService.update(sysUser));
    } else {
      this.subscribeToSaveResponse(this.sysUserService.create(sysUser));
    }
  }

  private createFromForm(): ISysUser {
    return {
      ...new SysUser(),
      id: this.editForm.get(['id']).value,
      userId: this.editForm.get(['userId']).value,
      status: this.editForm.get(['status']).value,
      name: this.editForm.get(['name']).value,
      sysPersonId: this.editForm.get(['sysPersonId']).value,
      groupIds: this.editForm.get(['groupIds']).value,
      enterpriseIds: this.editForm.get(['enterpriseIds']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysUser>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSysPersonById(index: number, item: ISysPerson) {
    return item.id;
  }

  trackSysGroupById(index: number, item: ISysGroup) {
    return item.id;
  }

  trackSysEnterpriseById(index: number, item: ISysEnterprise) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
