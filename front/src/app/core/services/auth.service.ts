import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, RegisterRequest, AuthResponse } from '../../shared/models/auth.model';

/**
 * Service responsable de la gestion de l'authentification des utilisateurs .
 * centralise les opérations liées à l'inscription,
 * à la connexion, à la gestion du token JWT et à la déconnexion
 * de l'utilisateur.
 *Le token JWT est stocké dans le {@code localStorage} du navigateur
 * afin de permettre à l'application de conserver l'état  d'authentification de l'utilisateur.
 */

@Injectable({
  providedIn: 'root'
})
export class AuthService {



  private apiUrl = environment.apiUrl;


//Initialise le service d'authentification avec le client HTTP Angular
  constructor(private http: HttpClient) {}





  /**
   * Enregistre un nouvel utilisateur.
   * Les informations d'inscription sont envoyées au endpoint
   * { /auth/register} du backend.
   *
   * @param request données nécessaires à l'inscription
   * @returns Observable contenant la réponse d'authentification :le token JWT
   */
  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/register`, request);
  }








  /**
   * Authentifie un utilisateur auprès de l'API.
   *Les identifiants fournis sont envoyés au endpoint {/auth/login} du backend.
   *
   * @param request données nécessaires à l'authentification
   * @returns Observable contenant la réponse d'authentification: le token JWT
   */

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/login`, request);
  }







/**
   * Enregistre le token JWT dans le stockage local du navigateur.
   *La clé utilisée pour stocker le token est définie dans
   * la configuration de l'environnement Angular.
   * @param token token JWT à enregistrer
   */

  saveToken(token: string): void {
    localStorage.setItem(environment.tokenKey, token);
  }






   /**
   * Récupère le token JWT actuellement enregistré.
   * @returns le token JWT s'il existe, sinon {@code null}
   */

  getToken(): string | null {
    return localStorage.getItem(environment.tokenKey);
  }






  /**
   * Vérifie si un token JWT est actuellement enregistré.
   */

  isAuthenticated(): boolean {
    return !!this.getToken();
  }





  /**
   * Déconnecte l'utilisateur en supprimant son token JWT du stockage local du navigateur.
   */
  
  logout(): void {
    localStorage.removeItem(environment.tokenKey);
  }
}