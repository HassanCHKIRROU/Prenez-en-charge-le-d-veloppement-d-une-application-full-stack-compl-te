import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { environment } from '../../../environments/environment';




describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;



  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });





  afterEach(() => {
    httpMock.verify();
  });





  it('should be created', () => {
    expect(service).toBeTruthy();
  });






  it('should get profile', () => {
    service.getProfile().subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/user/profile`);
    expect(req.request.method).toBe('GET');
    req.flush({});
  });





  

  it('should update profile', () => {
    const data = { username: 'new', email: 'new@test.com', password: 'NewTest1234!' };
    service.updateProfile(data).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/user/profile`);
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });
});