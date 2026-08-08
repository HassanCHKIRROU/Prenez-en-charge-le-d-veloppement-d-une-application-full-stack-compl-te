import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { DatePipe } from '@angular/common';
import { ProfileComponent } from './profile.component';
import { UserService } from '../../core/services/user.service';
import { AuthService } from '../../core/services/auth.service';
import { SubscriptionService } from '../../core/services/subscription.service';
import { MatIconModule } from '@angular/material/icon';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let userMock: any;
  let authMock: any;
  let subMock: any;
  let routerMock: any;



  beforeEach(() => {
    userMock = { getProfile: jasmine.createSpy().and.returnValue(of({ username: 'test', subscriptions: [] })) };
    authMock = { logout: jasmine.createSpy() };
    subMock = { unsubscribe: jasmine.createSpy().and.returnValue(of(null)) };
    routerMock = { navigate: jasmine.createSpy() };

    TestBed.configureTestingModule({
      declarations: [ProfileComponent],
      providers: [
        { provide: UserService, useValue: userMock },
        { provide: AuthService, useValue: authMock },
        { provide: SubscriptionService, useValue: subMock },
        { provide: Router, useValue: routerMock },
        DatePipe
      ]
    });

    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });






  it('should create', () => {
    expect(component).toBeTruthy();
  });




  it('should load profile', () => {
    component.ngOnInit();
    expect(userMock.getProfile).toHaveBeenCalled();
  });





  it('should unsubscribe', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.userProfile = { subscriptions: [{ id: 1 }] } as any;
    component.unsubscribe(1);
    expect(subMock.unsubscribe).toHaveBeenCalledWith(1);
  });





  
  it('should logout', () => {
    component.logout();
    expect(authMock.logout).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });
});