import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILead, Lead } from 'app/shared/model/lead.model';
import { LeadService } from './lead.service';

@Component({
  selector: 'jhi-lead-update',
  templateUrl: './lead-update.component.html'
})
export class LeadUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    source: [],
    lastName: [],
    firstName: [],
    title: [],
    companyName: [],
    state: [],
    country: [],
    county: [],
    city: [],
    zip: [],
    contacted: [],
    doNotContact: [],
    email: [],
    phone: []
  });

  constructor(protected leadService: LeadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lead }) => {
      this.updateForm(lead);
    });
  }

  updateForm(lead: ILead): void {
    this.editForm.patchValue({
      id: lead.id,
      source: lead.source,
      lastName: lead.lastName,
      firstName: lead.firstName,
      title: lead.title,
      companyName: lead.companyName,
      state: lead.state,
      country: lead.country,
      county: lead.county,
      city: lead.city,
      zip: lead.zip,
      contacted: lead.contacted,
      doNotContact: lead.doNotContact,
      email: lead.email,
      phone: lead.phone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lead = this.createFromForm();
    if (lead.id !== undefined) {
      this.subscribeToSaveResponse(this.leadService.update(lead));
    } else {
      this.subscribeToSaveResponse(this.leadService.create(lead));
    }
  }

  private createFromForm(): ILead {
    return {
      ...new Lead(),
      id: this.editForm.get(['id'])!.value,
      source: this.editForm.get(['source'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      title: this.editForm.get(['title'])!.value,
      companyName: this.editForm.get(['companyName'])!.value,
      state: this.editForm.get(['state'])!.value,
      country: this.editForm.get(['country'])!.value,
      county: this.editForm.get(['county'])!.value,
      city: this.editForm.get(['city'])!.value,
      zip: this.editForm.get(['zip'])!.value,
      contacted: this.editForm.get(['contacted'])!.value,
      doNotContact: this.editForm.get(['doNotContact'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILead>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
