import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISysIpGroup, SysIpGroup } from 'app/shared/model/sys-ip-group.model';
import { SysIpGroupService } from './sys-ip-group.service';
import { ISysGroup } from 'app/shared/model/sys-group.model';
import { SysGroupService } from 'app/entities/sys-group';
import { ISysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';
import { SysIpGroupItemService } from 'app/entities/sys-ip-group-item';

@Component({
  selector: 'jhi-sys-ip-group-update',
  templateUrl: './sys-ip-group-update.component.html'
})
export class SysIpGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  sysgroups: ISysGroup[];

  sysipgroupitems: ISysIpGroupItem[];

  editForm = this.fb.group({
    id: [],
    ipGroupId: [],
    sysIpGroupItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sysIpGroupService: SysIpGroupService,
    protected sysGroupService: SysGroupService,
    protected sysIpGroupItemService: SysIpGroupItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysIpGroup }) => {
      this.updateForm(sysIpGroup);
    });
    this.sysGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysGroup[]>) => response.body)
      )
      .subscribe((res: ISysGroup[]) => (this.sysgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.sysIpGroupItemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISysIpGroupItem[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISysIpGroupItem[]>) => response.body)
      )
      .subscribe((res: ISysIpGroupItem[]) => (this.sysipgroupitems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sysIpGroup: ISysIpGroup) {
    this.editForm.patchValue({
      id: sysIpGroup.id,
      ipGroupId: sysIpGroup.ipGroupId,
      sysIpGroupItemId: sysIpGroup.sysIpGroupItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysIpGroup = this.createFromForm();
    if (sysIpGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.sysIpGroupService.update(sysIpGroup));
    } else {
      this.subscribeToSaveResponse(this.sysIpGroupService.create(sysIpGroup));
    }
  }

  private createFromForm(): ISysIpGroup {
    return {
      ...new SysIpGroup(),
      id: this.editForm.get(['id']).value,
      ipGroupId: this.editForm.get(['ipGroupId']).value,
      sysIpGroupItemId: this.editForm.get(['sysIpGroupItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysIpGroup>>) {
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

  trackSysGroupById(index: number, item: ISysGroup) {
    return item.id;
  }

  trackSysIpGroupItemById(index: number, item: ISysIpGroupItem) {
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
