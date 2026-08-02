import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ArticleSummary, ArticleRequest, ArticleResponse } from '../../shared/models/article.model';
import { CommentRequest, CommentData } from '../../shared/models/comment.model';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl = environment.apiUrl;



  constructor(private http: HttpClient) {}





  getFeed(sort: string = 'desc'): Observable<ArticleSummary[]> {
    const params = new HttpParams().set('sort', sort);
    return this.http.get<ArticleSummary[]>(`${this.apiUrl}/articles/feed`, { params });
  }




  getArticle(id: number): Observable<ArticleResponse> {
    return this.http.get<ArticleResponse>(`${this.apiUrl}/articles/${id}`);
  }





  createArticle(request: any): Observable<ArticleResponse> {
    console.log('Envoi POST /api/articles avec:', request);
    return this.http.post<ArticleResponse>(`${this.apiUrl}/articles`, request);
  }




  addComment(articleId: number, request: CommentRequest): Observable<CommentData> {
    return this.http.post<CommentData>(`${this.apiUrl}/articles/${articleId}/comments`, request);
  }
}