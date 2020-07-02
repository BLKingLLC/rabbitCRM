import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RabbitCrmTestModule } from '../../../test.module';
import { LeadUpdateComponent } from 'app/entities/lead/lead-update.component';
import { LeadService } from 'app/entities/lead/lead.service';
import { Lead } from 'app/shared/model/lead.model';

describe('Component Tests', () => {
  describe('Lead Management Update Component', () => {
    let comp: LeadUpdateComponent;
    let fixture: ComponentFixture<LeadUpdateComponent>;
    let service: LeadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RabbitCrmTestModule],
        declarations: [LeadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LeadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LeadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LeadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Lead(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Lead();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
