import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, RegisterRequest, AuthResponse } from '../../shared/models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {



  private apiUrl = environment.apiUrl;



  constructor(private http: HttpClient) {}



  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/register`, request);
  }



  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/login`, request);
  }



  saveToken(token: string): void {
    localStorage.setItem(environment.tokenKey, token);
  }



  getToken(): string | null {
    return localStorage.getItem(environment.tokenKey);
  }



  isAuthenticated(): boolean {
    return !!this.getToken();
  }


  
  logout(): void {
    localStorage.removeItem(environment.tokenKey);
  }
}