import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISysEnterprise, SysEnterprise } from 'app/shared/model/sys-enterprise.model';
import { SysEnterpriseService } from './sys-enterprise.service';
import { ISysLicense } from 'app/shared/model/sys-license.model';
import { SysLicenseService } from 'app/entities/sys-license';
import { ISysGroup } from 'app/shared/model/sys-group.model';
import { SysGroupService } from 'app/entities/sys-group';
import { ISysUser } from 'app/shared/model/sys-user.model';
import { SysUserService } from 'app/entities/sys-user';

@Component({
  selector: 'jhi-sys-enterprise-update',
  templateUrl: './sys-enterprise-update.component.html'
})
export class SysEnterpriseUpdateComponent implements OnInit {
  isSaving: boolean;

  syslicenses: ISysLicense[];

  sysgroups: ISysGroup[];

  sysusers: ISysUser[];

  editForm = this.fb.group({
    id: [],
    enterpriseId: [],
    status: [],
    enterprise: [],
    alias: [],
    sysLicenseId: [],
    groupIds: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sysEnterpriseService: SysEnterpriseService,
    protected sysLicenseService: SysLicenseService,
    protected sysGroupService: SysGroupService,
    protected sysUserService: SysUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysEnterprise }) => {
      this.updateForm(sysEnterprise);
    });
    this.sysLicenseService
      .query({ filter: 'sysenterprise-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ISysLicense[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysLicense[]>) => response.body)
      )
      .subscribe(
        (res: ISysLicense[]) => {
          if (!!this.editForm.get('sysLicenseId').value) {
            this.syslicenses = res;
          } else {
            this.sysLicenseService
              .find(this.editForm.get('sysLicenseId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ISysLicense>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ISysLicense>) => subResponse.body)
              )
              .subscribe(
                (subRes: ISysLicense) => (this.syslicenses = [subRes].concat(res)),
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
    this.sysUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysUser[]>) => response.body)
      )
      .subscribe((res: ISysUser[]) => (this.sysusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sysEnterprise: ISysEnterprise) {
    this.editForm.patchValue({
      id: sysEnterprise.id,
      enterpriseId: sysEnterprise.enterpriseId,
      status: sysEnterprise.status,
      enterprise: sysEnterprise.enterprise,
      alias: sysEnterprise.alias,
      sysLicenseId: sysEnterprise.sysLicenseId,
      groupIds: sysEnterprise.groupIds
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysEnterprise = this.createFromForm();
    if (sysEnterprise.id !== undefined) {
      this.subscribeToSaveResponse(this.sysEnterpriseService.update(sysEnterprise));
    } else {
      this.subscribeToSaveResponse(this.sysEnterpriseService.create(sysEnterprise));
    }
  }

  private createFromForm(): ISysEnterprise {
    return {
      ...new SysEnterprise(),
      id: this.editForm.get(['id']).value,
      enterpriseId: this.editForm.get(['enterpriseId']).value,
      status: this.editForm.get(['status']).value,
      enterprise: this.editForm.get(['enterprise']).value,
      alias: this.editForm.get(['alias']).value,
      sysLicenseId: this.editForm.get(['sysLicenseId']).value,
      groupIds: this.editForm.get(['groupIds']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysEnterprise>>) {
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

  trackSysLicenseById(index: number, item: ISysLicense) {
    return item.id;
  }

  trackSysGroupById(index: number, item: ISysGroup) {
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
