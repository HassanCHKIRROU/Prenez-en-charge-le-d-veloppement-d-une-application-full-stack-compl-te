import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  start(): void {
    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/feed']);
    } else {
      this.router.navigate(['/login']);
    }
  }
}