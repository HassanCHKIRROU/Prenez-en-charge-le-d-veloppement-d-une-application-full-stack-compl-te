import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';



/**
 * Intercepteur HTTP chargé d'ajouter automatiquement le token JWT aux requêtes envoyées vers l'API.
 * Lorsqu'un token JWT est disponible, il est ajouté dans l'en-têteHTTP { Authorization} sous la forme 
 * {Bearer <token>}.
 * Cela permet au backend de vérifier l'authentification de l'utilisateur
 * lors des appels aux ressources protégées.
 * Si aucun token n'est disponible, la requête est transmise sans modification.
 */

@Injectable()
export class JwtInterceptor implements HttpInterceptor {



  constructor(private authService: AuthService) {}


  //Intercepte une requête HTTP avant son envoi vers le serveur.
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request);
  }
}