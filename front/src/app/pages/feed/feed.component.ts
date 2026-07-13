import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-feed',
  template: `
    <div class="feed-container">
      <h1>Mon Fil d'actualité</h1>
      <p>Bienvenue ! Vous êtes connecté.</p>
      <button mat-raised-button color="warn" (click)="logout()">Se déconnecter</button>
    </div>
  `,
  styles: [`
    .feed-container {
      padding: 40px;
      max-width: 800px;
      margin: 0 auto;
    }
  `]
})
export class FeedComponent {
  constructor(private authService: AuthService, private router: Router) {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}