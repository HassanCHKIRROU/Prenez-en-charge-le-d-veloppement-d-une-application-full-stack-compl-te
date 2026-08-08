import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TopicService } from './topic.service';
import { environment } from '../../../environments/environment';

describe('TopicService', () => {
  let service: TopicService;
  let httpMock: HttpTestingController;



  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TopicService]
    });
    service = TestBed.inject(TopicService);
    httpMock = TestBed.inject(HttpTestingController);
  });




  afterEach(() => {
    httpMock.verify();
  });





  it('should be created', () => {
    expect(service).toBeTruthy();
  });




  
  it('should get all topics', () => {
    service.getAllTopics().subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/topics`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });
});