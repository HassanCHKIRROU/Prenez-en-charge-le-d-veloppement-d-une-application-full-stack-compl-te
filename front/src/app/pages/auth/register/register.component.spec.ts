import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RegisterComponent } from './register.component';
import { AuthService } from 'src/app/core/services/auth.service';
import { MatIconModule } from '@angular/material/icon';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authMock: any;
  let routerMock: any;



  beforeEach(() => {
    authMock = { register: jasmine.createSpy().and.returnValue(of({ token: '123' })), saveToken: jasmine.createSpy() };
    routerMock = { navigate: jasmine.createSpy() };

    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        MatFormFieldModule,
        MatInputModule,
        NoopAnimationsModule
      ],
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });





  it('should create', () => {
    expect(component).toBeTruthy();
  });






  it('should have empty form', () => {
    expect(component.registerForm.get('username')?.value).toBe('');
    expect(component.registerForm.get('email')?.value).toBe('');
    expect(component.registerForm.get('password')?.value).toBe('');
  });






  it('should register and redirect', () => {
    component.registerForm.setValue({ username: 'test', email: 'test@test.com', password: 'Test1234!' });
    component.onSubmit();
    expect(authMock.register).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
  });





  

  it('should show error on register failure', () => {
    authMock.register.and.returnValue(throwError(() => ({ error: { message: 'Error' } })));
    component.registerForm.setValue({ username: 'test', email: 'test@test.com', password: 'Test1234!' });
    component.onSubmit();
    //Forcer la détection des changements pour mettre à jour le template
    fixture.detectChanges();
    expect(component.errorMessage).toBe('Error');
  });
});