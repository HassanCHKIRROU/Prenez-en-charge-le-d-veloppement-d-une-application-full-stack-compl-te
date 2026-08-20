import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ArticleSummary, ArticleRequest, ArticleResponse } from '../../shared/models/article.model';
import { CommentRequest, CommentData } from '../../shared/models/comment.model';

/**
 * Service responsable de la communication entre l'application Angular 
 * et l'API backend concernant les articles et leurs commentaires.
 *
 *Ce service centralise les requêtes HTTP permettant notamment de :
 *   récupérer le fil d'actualité des articles 
 *   récupérer le détail d'un article 
 *   créer un nouvel article 
 *   ajouter un commentaire à un article.
 * Les requêtes sont envoyées vers l'API dont l'URL de base
 * est définie dans la configuration de l'environnement Angular
 */

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private apiUrl = environment.apiUrl;



  constructor(private http: HttpClient) {}




    /**
   * Récupère le fil d'actualité des articles.
   *Les articles sont triés selon le paramètre fourni.
   * Par défaut, le tri est effectué dans l'ordre décroissant.
   */

  getFeed(sort: string = 'desc'): Observable<ArticleSummary[]> {
    const params = new HttpParams().set('sort', sort);
    return this.http.get<ArticleSummary[]>(`${this.apiUrl}/articles/feed`, { params });
  }




  /**
   * Récupère les informations détaillées d'un article.
   */

  getArticle(id: number): Observable<ArticleResponse> {
    return this.http.get<ArticleResponse>(`${this.apiUrl}/articles/${id}`);
  }






  /**
   * Crée un nouvel article.
   * Les données fournies sont envoyées à l'API backend
   * via une requête HTTP POST.
   */

  createArticle(request: any): Observable<ArticleResponse> {
    console.log('Envoi POST /api/articles avec:', request);
    return this.http.post<ArticleResponse>(`${this.apiUrl}/articles`, request);
  }




  /**
   * Ajoute un commentaire à un article.
   * Le commentaire est envoyé à l'API backend en utilisant
   * l'identifiant de l'article concerné.
   */

  addComment(articleId: number, request: CommentRequest): Observable<CommentData> {
    return this.http.post<CommentData>(`${this.apiUrl}/articles/${articleId}/comments`, request);
  }
}