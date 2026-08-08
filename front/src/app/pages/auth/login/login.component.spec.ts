import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login.component';
import { AuthService } from 'src/app/core/services/auth.service';
import { MatIconModule } from '@angular/material/icon';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authMock: any;
  let routerMock: any;




  beforeEach(() => {
    authMock = { login: jasmine.createSpy().and.returnValue(of({ token: '123' })), saveToken: jasmine.createSpy() };
    routerMock = { navigate: jasmine.createSpy() };

    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        MatFormFieldModule,
        MatInputModule,
        NoopAnimationsModule
      ],
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useValue: authMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });







  it('should create', () => {
    expect(component).toBeTruthy();
  });





  it('should have empty form', () => {
    expect(component.loginForm.get('usernameOrEmail')?.value).toBe('');
    expect(component.loginForm.get('password')?.value).toBe('');
  });







  it('should login and redirect', () => {
    component.loginForm.setValue({ usernameOrEmail: 'test', password: 'Test1234!' });
    component.onSubmit();
    expect(authMock.login).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
  });


});