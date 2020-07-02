import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILead } from 'app/shared/model/lead.model';
import { LeadService } from './lead.service';
import { LeadDeleteDialogComponent } from './lead-delete-dialog.component';

@Component({
  selector: 'jhi-lead',
  templateUrl: './lead.component.html'
})
export class LeadComponent implements OnInit, OnDestroy {
  leads?: ILead[];
  eventSubscriber?: Subscription;

  constructor(protected leadService: LeadService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.leadService.query().subscribe((res: HttpResponse<ILead[]>) => {
      this.leads = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLeads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILead): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLeads(): void {
    this.eventSubscriber = this.eventManager.subscribe('leadListModification', () => this.loadAll());
  }

  delete(lead: ILead): void {
    const modalRef = this.modalService.open(LeadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lead = lead;
  }
}
