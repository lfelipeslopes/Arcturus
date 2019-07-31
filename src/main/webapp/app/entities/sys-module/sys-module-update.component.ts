import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISysModule, SysModule } from 'app/shared/model/sys-module.model';
import { SysModuleService } from './sys-module.service';

@Component({
  selector: 'jhi-sys-module-update',
  templateUrl: './sys-module-update.component.html'
})
export class SysModuleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    moduleId: [],
    moduleCode: [],
    moduleDescription: []
  });

  constructor(protected sysModuleService: SysModuleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sysModule }) => {
      this.updateForm(sysModule);
    });
  }

  updateForm(sysModule: ISysModule) {
    this.editForm.patchValue({
      id: sysModule.id,
      moduleId: sysModule.moduleId,
      moduleCode: sysModule.moduleCode,
      moduleDescription: sysModule.moduleDescription
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sysModule = this.createFromForm();
    if (sysModule.id !== undefined) {
      this.subscribeToSaveResponse(this.sysModuleService.update(sysModule));
    } else {
      this.subscribeToSaveResponse(this.sysModuleService.create(sysModule));
    }
  }

  private createFromForm(): ISysModule {
    return {
      ...new SysModule(),
      id: this.editForm.get(['id']).value,
      moduleId: this.editForm.get(['moduleId']).value,
      moduleCode: this.editForm.get(['moduleCode']).value,
      moduleDescription: this.editForm.get(['moduleDescription']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISysModule>>) {
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
