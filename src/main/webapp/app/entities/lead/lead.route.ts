import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILead, Lead } from 'app/shared/model/lead.model';
import { LeadService } from './lead.service';
import { LeadComponent } from './lead.component';
import { LeadDetailComponent } from './lead-detail.component';
import { LeadUpdateComponent } from './lead-update.component';

@Injectable({ providedIn: 'root' })
export class LeadResolve implements Resolve<ILead> {
  constructor(private service: LeadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILead> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lead: HttpResponse<Lead>) => {
          if (lead.body) {
            return of(lead.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lead());
  }
}

export const leadRoute: Routes = [
  {
    path: '',
    component: LeadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Leads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LeadDetailComponent,
    resolve: {
      lead: LeadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Leads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LeadUpdateComponent,
    resolve: {
      lead: LeadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Leads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LeadUpdateComponent,
    resolve: {
      lead: LeadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Leads'
    },
    canActivate: [UserRouteAccessService]
  }
];
