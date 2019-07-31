import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISysGroup, SysGroup } from 'app/shared/model/sys-group.model';
import { SysGroupService } from './sys-group.service';
import { ISysIpGroup } from 'app/shared/model/sys-ip-group.model';
import { SysIpGroupService } from 'app/entities/sys-ip-group';
import { ISysAccess } from 'app/shared/model/sys-access.model';
import { SysAccessService } from 'app/entities/sys-access';
import { ISysEnterprise } from 'app/shared/model/sys-enterprise.model';
import { SysEnterpriseService } from 'app/entities/sys-enterprise';
import { ISysUser } from 'app/shared/model/sys-user.model';
import { SysUserService } from 'app/entities/sys-user';

@Component({
  selector: 'jhi-sys-group-update',
  templateUrl: './sys-group-update.component.html'
})
export class SysGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  sysipgroups: ISysIpGroup[];

  sysaccesses: ISysAccess[];

  sysenterprises: ISysEnterprise[];

  sysusers: ISysUser[];

  editForm = this.fb.group({
    id: [],
    groupId: [],
    status: [],
    group: [],
    ipGroupIds: [],
    accessIds: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sysGroupService: SysGroupService,
    protected sysIpGroupService: SysIpGroupService,
    protected sysAccessService: SysAccessService,
    protected sysEnterpriseService: SysEnterpriseService,
    protected sysUserService: SysUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysGroup }) => {
      this.updateForm(sysGroup);
    });
    this.sysIpGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysIpGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysIpGroup[]>) => response.body)
      )
      .subscribe((res: ISysIpGroup[]) => (this.sysipgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sysAccessService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysAccess[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysAccess[]>) => response.body)
      )
      .subscribe((res: ISysAccess[]) => (this.sysaccesses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sysEnterpriseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysEnterprise[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysEnterprise[]>) => response.body)
      )
      .subscribe((res: ISysEnterprise[]) => (this.sysenterprises = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sysUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysUser[]>) => response.body)
      )
      .subscribe((res: ISysUser[]) => (this.sysusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sysGroup: ISysGroup) {
    this.editForm.patchValue({
      id: sysGroup.id,
      groupId: sysGroup.groupId,
      status: sysGroup.status,
      group: sysGroup.group,
      ipGroupIds: sysGroup.ipGroupIds,
      accessIds: sysGroup.accessIds
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysGroup = this.createFromForm();
    if (sysGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.sysGroupService.update(sysGroup));
    } else {
      this.subscribeToSaveResponse(this.sysGroupService.create(sysGroup));
    }
  }

  private createFromForm(): ISysGroup {
    return {
      ...new SysGroup(),
      id: this.editForm.get(['id']).value,
      groupId: this.editForm.get(['groupId']).value,
      status: this.editForm.get(['status']).value,
      group: this.editForm.get(['group']).value,
      ipGroupIds: this.editForm.get(['ipGroupIds']).value,
      accessIds: this.editForm.get(['accessIds']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysGroup>>) {
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

  trackSysIpGroupById(index: number, item: ISysIpGroup) {
    return item.id;
  }

  trackSysAccessById(index: number, item: ISysAccess) {
    return item.id;
  }

  trackSysEnterpriseById(index: number, item: ISysEnterprise) {
    return item.id;
  }

  trackSysUserById(index: number, item: ISysUser) {
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
