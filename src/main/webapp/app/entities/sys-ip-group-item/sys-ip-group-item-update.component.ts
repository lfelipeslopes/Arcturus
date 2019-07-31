import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISysIpGroupItem, SysIpGroupItem } from 'app/shared/model/sys-ip-group-item.model';
import { SysIpGroupItemService } from './sys-ip-group-item.service';

@Component({
  selector: 'jhi-sys-ip-group-item-update',
  templateUrl: './sys-ip-group-item-update.component.html'
})
export class SysIpGroupItemUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    ipGroupItemId: [],
    initialIp: [],
    finalIp: []
  });

  constructor(protected sysIpGroupItemService: SysIpGroupItemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysIpGroupItem }) => {
      this.updateForm(sysIpGroupItem);
    });
  }

  updateForm(sysIpGroupItem: ISysIpGroupItem) {
    this.editForm.patchValue({
      id: sysIpGroupItem.id,
      ipGroupItemId: sysIpGroupItem.ipGroupItemId,
      initialIp: sysIpGroupItem.initialIp,
      finalIp: sysIpGroupItem.finalIp
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysIpGroupItem = this.createFromForm();
    if (sysIpGroupItem.id !== undefined) {
      this.subscribeToSaveResponse(this.sysIpGroupItemService.update(sysIpGroupItem));
    } else {
      this.subscribeToSaveResponse(this.sysIpGroupItemService.create(sysIpGroupItem));
    }
  }

  private createFromForm(): ISysIpGroupItem {
    return {
      ...new SysIpGroupItem(),
      id: this.editForm.get(['id']).value,
      ipGroupItemId: this.editForm.get(['ipGroupItemId']).value,
      initialIp: this.editForm.get(['initialIp']).value,
      finalIp: this.editForm.get(['finalIp']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysIpGroupItem>>) {
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
