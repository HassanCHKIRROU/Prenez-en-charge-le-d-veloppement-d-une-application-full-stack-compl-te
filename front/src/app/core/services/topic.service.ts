import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TopicDTO } from '../../shared/models/topic.model';

/**
 * Service responsable de la gestion des thèmes de l'application.
 *Ce service centralise les communications HTTP avec l'API backend liées à la récupération des thèmes disponibles.</p>
 */

@Injectable({
  providedIn: 'root'
})
export class TopicService {


  private apiUrl = environment.apiUrl;



  constructor(private http: HttpClient) {}





  /**
   * Récupère la liste de tous les thèmes disponibles.
   *Les thèmes sont récupérés depuis l'API backend et retournéssous forme d'une liste de { TopicDTO}.
   *
   * @returns Observable contenant la liste des thèmes disponibles
   */
  
  getAllTopics(): Observable<TopicDTO[]> {
    return this.http.get<TopicDTO[]>(`${this.apiUrl}/topics`);
  }
}