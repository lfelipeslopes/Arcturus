import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISysAccess, SysAccess } from 'app/shared/model/sys-access.model';
import { SysAccessService } from './sys-access.service';
import { ISysModule } from 'app/shared/model/sys-module.model';
import { SysModuleService } from 'app/entities/sys-module';
import { ISysGroup } from 'app/shared/model/sys-group.model';
import { SysGroupService } from 'app/entities/sys-group';

@Component({
  selector: 'jhi-sys-access-update',
  templateUrl: './sys-access-update.component.html'
})
export class SysAccessUpdateComponent implements OnInit {
  isSaving: boolean;

  sysmodules: ISysModule[];

  sysgroups: ISysGroup[];

  editForm = this.fb.group({
    id: [],
    accessId: [],
    status: [],
    access: [],
    description: [],
    sysModuleId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sysAccessService: SysAccessService,
    protected sysModuleService: SysModuleService,
    protected sysGroupService: SysGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysAccess }) => {
      this.updateForm(sysAccess);
    });
    this.sysModuleService
      .query({ filter: 'sysaccess-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ISysModule[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysModule[]>) => response.body)
      )
      .subscribe(
        (res: ISysModule[]) => {
          if (!!this.editForm.get('sysModuleId').value) {
            this.sysmodules = res;
          } else {
            this.sysModuleService
              .find(this.editForm.get('sysModuleId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ISysModule>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ISysModule>) => subResponse.body)
              )
              .subscribe(
                (subRes: ISysModule) => (this.sysmodules = [subRes].concat(res)),
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
  }

  updateForm(sysAccess: ISysAccess) {
    this.editForm.patchValue({
      id: sysAccess.id,
      accessId: sysAccess.accessId,
      status: sysAccess.status,
      access: sysAccess.access,
      description: sysAccess.description,
      sysModuleId: sysAccess.sysModuleId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysAccess = this.createFromForm();
    if (sysAccess.id !== undefined) {
      this.subscribeToSaveResponse(this.sysAccessService.update(sysAccess));
    } else {
      this.subscribeToSaveResponse(this.sysAccessService.create(sysAccess));
    }
  }

  private createFromForm(): ISysAccess {
    return {
      ...new SysAccess(),
      id: this.editForm.get(['id']).value,
      accessId: this.editForm.get(['accessId']).value,
      status: this.editForm.get(['status']).value,
      access: this.editForm.get(['access']).value,
      description: this.editForm.get(['description']).value,
      sysModuleId: this.editForm.get(['sysModuleId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysAccess>>) {
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

  trackSysModuleById(index: number, item: ISysModule) {
    return item.id;
  }

  trackSysGroupById(index: number, item: ISysGroup) {
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
