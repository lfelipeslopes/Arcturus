import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISysPersonType, SysPersonType } from 'app/shared/model/sys-person-type.model';
import { SysPersonTypeService } from './sys-person-type.service';

@Component({
  selector: 'jhi-sys-person-type-update',
  templateUrl: './sys-person-type-update.component.html'
})
export class SysPersonTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    personTypeId: [],
    personTypeCode: [],
    personTypeDescription: []
  });

  constructor(protected sysPersonTypeService: SysPersonTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysPersonType }) => {
      this.updateForm(sysPersonType);
    });
  }

  updateForm(sysPersonType: ISysPersonType) {
    this.editForm.patchValue({
      id: sysPersonType.id,
      personTypeId: sysPersonType.personTypeId,
      personTypeCode: sysPersonType.personTypeCode,
      personTypeDescription: sysPersonType.personTypeDescription
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysPersonType = this.createFromForm();
    if (sysPersonType.id !== undefined) {
      this.subscribeToSaveResponse(this.sysPersonTypeService.update(sysPersonType));
    } else {
      this.subscribeToSaveResponse(this.sysPersonTypeService.create(sysPersonType));
    }
  }

  private createFromForm(): ISysPersonType {
    return {
      ...new SysPersonType(),
      id: this.editForm.get(['id']).value,
      personTypeId: this.editForm.get(['personTypeId']).value,
      personTypeCode: this.editForm.get(['personTypeCode']).value,
      personTypeDescription: this.editForm.get(['personTypeDescription']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysPersonType>>) {
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
