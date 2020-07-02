import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RabbitCrmTestModule } from '../../../test.module';
import { LeadComponent } from 'app/entities/lead/lead.component';
import { LeadService } from 'app/entities/lead/lead.service';
import { Lead } from 'app/shared/model/lead.model';

describe('Component Tests', () => {
  describe('Lead Management Component', () => {
    let comp: LeadComponent;
    let fixture: ComponentFixture<LeadComponent>;
    let service: LeadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RabbitCrmTestModule],
        declarations: [LeadComponent],
        providers: []
      })
        .overrideTemplate(LeadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Lead(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.leads && comp.leads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
