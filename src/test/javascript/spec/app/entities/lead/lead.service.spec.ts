import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { LeadService } from 'app/entities/lead/lead.service';
import { ILead, Lead } from 'app/shared/model/lead.model';

describe('Service Tests', () => {
  describe('Lead Service', () => {
    let injector: TestBed;
    let service: LeadService;
    let httpMock: HttpTestingController;
    let elemDefault: ILead;
    let expectedResult: ILead | ILead[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LeadService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Lead(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Lead', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Lead())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Lead', () => {
        const returnedFromService = Object.assign(
          {
            source: 'BBBBBB',
            lastName: 'BBBBBB',
            firstName: 'BBBBBB',
            title: 'BBBBBB',
            companyName: 'BBBBBB',
            state: 'BBBBBB',
            country: 'BBBBBB',
            county: 'BBBBBB',
            city: 'BBBBBB',
            zip: 'BBBBBB',
            contacted: true,
            doNotContact: true,
            email: 'BBBBBB',
            phone: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Lead', () => {
        const returnedFromService = Object.assign(
          {
            source: 'BBBBBB',
            lastName: 'BBBBBB',
            firstName: 'BBBBBB',
            title: 'BBBBBB',
            companyName: 'BBBBBB',
            state: 'BBBBBB',
            country: 'BBBBBB',
            county: 'BBBBBB',
            city: 'BBBBBB',
            zip: 'BBBBBB',
            contacted: true,
            doNotContact: true,
            email: 'BBBBBB',
            phone: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Lead', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
