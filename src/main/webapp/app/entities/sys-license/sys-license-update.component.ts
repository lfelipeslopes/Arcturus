import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ISysLicense, SysLicense } from 'app/shared/model/sys-license.model';
import { SysLicenseService } from './sys-license.service';

@Component({
  selector: 'jhi-sys-license-update',
  templateUrl: './sys-license-update.component.html'
})
export class SysLicenseUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    licenseId: [],
    status: [],
    licenseKey: [],
    startDate: [],
    endDate: [],
    mainKey: []
  });

  constructor(protected sysLicenseService: SysLicenseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysLicense }) => {
      this.updateForm(sysLicense);
    });
  }

  updateForm(sysLicense: ISysLicense) {
    this.editForm.patchValue({
      id: sysLicense.id,
      licenseId: sysLicense.licenseId,
      status: sysLicense.status,
      licenseKey: sysLicense.licenseKey,
      startDate: sysLicense.startDate != null ? sysLicense.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: sysLicense.endDate != null ? sysLicense.endDate.format(DATE_TIME_FORMAT) : null,
      mainKey: sysLicense.mainKey
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysLicense = this.createFromForm();
    if (sysLicense.id !== undefined) {
      this.subscribeToSaveResponse(this.sysLicenseService.update(sysLicense));
    } else {
      this.subscribeToSaveResponse(this.sysLicenseService.create(sysLicense));
    }
  }

  private createFromForm(): ISysLicense {
    return {
      ...new SysLicense(),
      id: this.editForm.get(['id']).value,
      licenseId: this.editForm.get(['licenseId']).value,
      status: this.editForm.get(['status']).value,
      licenseKey: this.editForm.get(['licenseKey']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      mainKey: this.editForm.get(['mainKey']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysLicense>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
