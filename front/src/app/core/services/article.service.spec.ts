import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ArticleService } from './article.service';
import { environment } from '../../../environments/environment';

describe('ArticleService', () => {
  let service: ArticleService;
  let httpMock: HttpTestingController;



  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ArticleService]
    });
    service = TestBed.inject(ArticleService);
    httpMock = TestBed.inject(HttpTestingController);
  });




  afterEach(() => {
    httpMock.verify();
  });




  it('should be created', () => {
    expect(service).toBeTruthy();
  });




  it('should get feed', () => {
    service.getFeed('desc').subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/articles/feed?sort=desc`);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });






  it('should get article by id', () => {
    service.getArticle(1).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/articles/1`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });





  it('should create article', () => {
    const data = { topicName: 'Java', title: 'Test', content: 'Content' };
    service.createArticle(data).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/articles`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });



  

  it('should add comment', () => {
    const data = { content: 'Comment' };
    service.addComment(1, data).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/articles/1/comments`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });
});