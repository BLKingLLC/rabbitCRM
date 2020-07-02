import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RabbitCrmTestModule } from '../../../test.module';
import { LeadDetailComponent } from 'app/entities/lead/lead-detail.component';
import { Lead } from 'app/shared/model/lead.model';

describe('Component Tests', () => {
  describe('Lead Management Detail Component', () => {
    let comp: LeadDetailComponent;
    let fixture: ComponentFixture<LeadDetailComponent>;
    const route = ({ data: of({ lead: new Lead(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RabbitCrmTestModule],
        declarations: [LeadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LeadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LeadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lead on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lead).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
