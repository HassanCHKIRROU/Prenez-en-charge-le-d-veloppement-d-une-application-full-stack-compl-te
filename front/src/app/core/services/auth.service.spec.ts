import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;


  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });



  afterEach(() => {
    httpMock.verify();
  });





  it('should be created', () => {
    expect(service).toBeTruthy();
  });





  it('should register', () => {
    const data = { username: 'test', email: 'test@test.com', password: 'Test1234!' };
    service.register(data).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/auth/register`);
    expect(req.request.method).toBe('POST');
    req.flush({ token: '123' });
  });







  it('should login', () => {
    const data = { usernameOrEmail: 'test', password: 'Test1234!' };
    service.login(data).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush({ token: '123' });
  });





  it('should save token', () => {
    service.saveToken('abc');
    expect(localStorage.getItem(environment.tokenKey)).toBe('abc');
  });







  it('should get token', () => {
    localStorage.setItem(environment.tokenKey, 'abc');
    expect(service.getToken()).toBe('abc');
  });






  it('should return true if authenticated', () => {
    localStorage.setItem(environment.tokenKey, 'abc');
    expect(service.isAuthenticated()).toBe(true);
  });





  it('should return false if not authenticated', () => {
    localStorage.removeItem(environment.tokenKey);
    expect(service.isAuthenticated()).toBe(false);
  });



  

  it('should logout', () => {
    localStorage.setItem(environment.tokenKey, 'abc');
    service.logout();
    expect(localStorage.getItem(environment.tokenKey)).toBeNull();
  });
});