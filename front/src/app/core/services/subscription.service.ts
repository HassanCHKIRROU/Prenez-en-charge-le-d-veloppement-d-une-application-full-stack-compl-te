import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

/**
 * Service responsable de la gestion des abonnements de l'utilisateur aux différents thèmes de l'application.
 *Ce service centralise les communications HTTP avec l'API backend
 * pour permettre à l'utilisateur de s'abonner ou de se désabonner d'un thème.
 */

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {


  private apiUrl = environment.apiUrl;


  constructor(private http: HttpClient) {}






  /**
   * Abonne l'utilisateur au thème correspondant à l'identifiant fourni.
   *Une requête HTTP POST est envoyée à l'API backend afin de créer l'abonnement.
   *
   * @param topicId identifiant du thème auquel l'utilisateur souhaite s'abonner
   * @returns Observable indiquant la fin de l'opération
   */

  subscribe(topicId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/subscriptions/${topicId}`, {});
  }







  /**
   * Désabonne l'utilisateur du thème correspondant à l'identifiant fourni.
   *Une requête HTTP DELETE est envoyée à l'API backend pour supprimer l'abonnement existant.
   *
   * @param topicId identifiant du thème dont l'utilisateur souhaite se désabonner
   * @returns Observable indiquant la fin de l'opération
   */
  
  unsubscribe(topicId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/subscriptions/${topicId}`);
  }
}