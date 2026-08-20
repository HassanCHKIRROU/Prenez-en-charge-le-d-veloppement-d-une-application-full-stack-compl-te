import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { UpdateProfileRequest, UserProfileResponse } from '../../shared/models/user.model';

/**
 * Service responsable de la gestion du profil de l'utilisateur.
 *
 * Ce service centralise les communications HTTP avec l'API backend
 * permettant de consulter et de modifier les informations du profil
 * de l'utilisateur actuellement authentifié.
 */

@Injectable({
  providedIn: 'root'
})
export class UserService {


  //URL de base de l'API backend.
  private apiUrl = environment.apiUrl;



  //Initialise le service utilisateur avec le client HTTP Angular
  constructor(private http: HttpClient) {}







  /**
   * Récupère le profil de l'utilisateur actuellement authentifié.
   *Une requête HTTP GET est envoyée au backend afin de récupérer
   * les informations du profil ainsi que les données associées à l'utilisateur.
   *
   * @returns Observable contenant les informations du profil utilisateur
   */
  getProfile(): Observable<UserProfileResponse> {
    return this.http.get<UserProfileResponse>(`${this.apiUrl}/user/profile`);
  }

  




  
  /**
   * Met à jour le profil de l'utilisateur actuellement authentifié.
   *Les nouvelles informations du profil sont transmises au backend via une requête HTTP PUT.
   *
   * @param request données contenant les nouvelles informations du profil utilisateur
   * @returns Observable contenant le profil utilisateur mis à jour
   */
  updateProfile(request: UpdateProfileRequest): Observable<UserProfileResponse> {
    return this.http.put<UserProfileResponse>(`${this.apiUrl}/user/profile`, request);
  }
}