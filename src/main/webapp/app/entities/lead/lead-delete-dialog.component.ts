import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILead } from 'app/shared/model/lead.model';
import { LeadService } from './lead.service';

@Component({
  templateUrl: './lead-delete-dialog.component.html'
})
export class LeadDeleteDialogComponent {
  lead?: ILead;

  constructor(protected leadService: LeadService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('leadListModification');
      this.activeModal.close();
    });
  }
}
