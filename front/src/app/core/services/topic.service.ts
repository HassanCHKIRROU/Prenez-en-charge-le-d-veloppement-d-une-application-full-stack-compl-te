import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TopicDTO } from '../../shared/models/topic.model';

@Injectable({
  providedIn: 'root'
})
export class TopicService {


  private apiUrl = environment.apiUrl;



  constructor(private http: HttpClient) {}




  
  getAllTopics(): Observable<TopicDTO[]> {
    return this.http.get<TopicDTO[]>(`${this.apiUrl}/topics`);
  }
}