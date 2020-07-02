import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILead } from 'app/shared/model/lead.model';

type EntityResponseType = HttpResponse<ILead>;
type EntityArrayResponseType = HttpResponse<ILead[]>;

@Injectable({ providedIn: 'root' })
export class LeadService {
  public resourceUrl = SERVER_API_URL + 'api/leads';

  constructor(protected http: HttpClient) {}

  create(lead: ILead): Observable<EntityResponseType> {
    return this.http.post<ILead>(this.resourceUrl, lead, { observe: 'response' });
  }

  update(lead: ILead): Observable<EntityResponseType> {
    return this.http.put<ILead>(this.resourceUrl, lead, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILead>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILead[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
