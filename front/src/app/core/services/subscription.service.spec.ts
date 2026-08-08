import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SubscriptionService } from './subscription.service';
import { environment } from '../../../environments/environment';

describe('SubscriptionService', () => {
  let service: SubscriptionService;
  let httpMock: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SubscriptionService]
    });
    service = TestBed.inject(SubscriptionService);
    httpMock = TestBed.inject(HttpTestingController);
  });




  afterEach(() => {
    httpMock.verify();
  });






  it('should be created', () => {
    expect(service).toBeTruthy();
  });





  it('should subscribe', () => {
    service.subscribe(1).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/subscriptions/1`);
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });






  
  it('should unsubscribe', () => {
    service.unsubscribe(1).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/subscriptions/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});