import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RabbitCrmSharedModule } from 'app/shared/shared.module';
import { LeadComponent } from './lead.component';
import { LeadDetailComponent } from './lead-detail.component';
import { LeadUpdateComponent } from './lead-update.component';
import { LeadDeleteDialogComponent } from './lead-delete-dialog.component';
import { leadRoute } from './lead.route';

@NgModule({
  imports: [RabbitCrmSharedModule, RouterModule.forChild(leadRoute)],
  declarations: [LeadComponent, LeadDetailComponent, LeadUpdateComponent, LeadDeleteDialogComponent],
  entryComponents: [LeadDeleteDialogComponent]
})
export class RabbitCrmLeadModule {}
