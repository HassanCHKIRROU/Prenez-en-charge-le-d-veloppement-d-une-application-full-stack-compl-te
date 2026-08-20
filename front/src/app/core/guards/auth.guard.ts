import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';


/**
 * Guard permettant de controler l'accès aux routes nécessitant une authentification.
* Le guard vérifie si l'utilisateur est actuellement authentifié
 *  Si l'utilisateur est authentifié,
 * l'accès à la route est autorisé. Dans le cas contraire, il est
 * redirigé vers la page de connexion.</p>
 */

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {



  constructor(private authService: AuthService, private router: Router) {}



 
  
  // Vérifie si l'utilisateur peut accéder à la route demandée.
  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      return true;
    }

    this.router.navigate(['/login']);
    return false;
  }
}