import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'lead',
        loadChildren: () => import('./lead/lead.module').then(m => m.RabbitCrmLeadModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class RabbitCrmEntityModule {}
