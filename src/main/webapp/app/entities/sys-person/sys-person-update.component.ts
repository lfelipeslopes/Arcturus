import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISysPerson, SysPerson } from 'app/shared/model/sys-person.model';
import { SysPersonService } from './sys-person.service';
import { ISysPersonType } from 'app/shared/model/sys-person-type.model';
import { SysPersonTypeService } from 'app/entities/sys-person-type';

@Component({
  selector: 'jhi-sys-person-update',
  templateUrl: './sys-person-update.component.html'
})
export class SysPersonUpdateComponent implements OnInit {
  isSaving: boolean;

  syspersontypes: ISysPersonType[];

  editForm = this.fb.group({
    id: [],
    personId: [],
    personDescription: [],
    personContact: [],
    sysPersonTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sysPersonService: SysPersonService,
    protected sysPersonTypeService: SysPersonTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysPerson }) => {
      this.updateForm(sysPerson);
    });
    this.sysPersonTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysPersonType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysPersonType[]>) => response.body)
      )
      .subscribe((res: ISysPersonType[]) => (this.syspersontypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sysPerson: ISysPerson) {
    this.editForm.patchValue({
      id: sysPerson.id,
      personId: sysPerson.personId,
      personDescription: sysPerson.personDescription,
      personContact: sysPerson.personContact,
      sysPersonTypeId: sysPerson.sysPersonTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysPerson = this.createFromForm();
    if (sysPerson.id !== undefined) {
      this.subscribeToSaveResponse(this.sysPersonService.update(sysPerson));
    } else {
      this.subscribeToSaveResponse(this.sysPersonService.create(sysPerson));
    }
  }

  private createFromForm(): ISysPerson {
    return {
      ...new SysPerson(),
      id: this.editForm.get(['id']).value,
      personId: this.editForm.get(['personId']).value,
      personDescription: this.editForm.get(['personDescription']).value,
      personContact: this.editForm.get(['personContact']).value,
      sysPersonTypeId: this.editForm.get(['sysPersonTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysPerson>>) {
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

  trackSysPersonTypeById(index: number, item: ISysPersonType) {
    return item.id;
  }
}
