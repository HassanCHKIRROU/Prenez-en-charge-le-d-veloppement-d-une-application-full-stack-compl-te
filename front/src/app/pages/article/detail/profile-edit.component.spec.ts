import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ProfileEditComponent } from 'src/app/pages/profile/edit/profile-edit.component';
import { UserService } from 'src/app/core/services/user.service';
import { AuthService } from 'src/app/core/services/auth.service'; 

describe('ProfileEditComponent', () => {
  let component: ProfileEditComponent;
  let fixture: ComponentFixture<ProfileEditComponent>;
  let userMock: any;
  let routerMock: any;
  let authMock: any; 

  beforeEach(() => {
    userMock = {
      getProfile: jasmine.createSpy().and.returnValue(of({ username: 'test', email: 'test@test.com' })),
      updateProfile: jasmine.createSpy().and.returnValue(of({}))
    };
    routerMock = { navigate: jasmine.createSpy() };
    authMock = { isAuthenticated: jasmine.createSpy().and.returnValue(true) }; 

    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        MatFormFieldModule,
        MatInputModule,
        NoopAnimationsModule
      ],
      declarations: [ProfileEditComponent],
      providers: [
        { provide: UserService, useValue: userMock },
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authMock } 
      ]
    });

    fixture = TestBed.createComponent(ProfileEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load profile', () => {
    component.ngOnInit();
    expect(component.profileForm.get('username')?.value).toBe('test');
  });

  it('should update profile', () => {
    component.profileForm.setValue({ username: 'new', email: 'new@test.com', password: '', confirmPassword: '' });
    component.onSubmit();
    expect(userMock.updateProfile).toHaveBeenCalled();
  });

  it('should cancel', () => {
    component.cancel();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/profile']);
  });
});