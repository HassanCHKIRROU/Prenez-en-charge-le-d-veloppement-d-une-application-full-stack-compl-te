import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})

export class HeaderComponent {



  constructor(
    private authService: AuthService,
    private router: Router
  ) {}





  goToFeed(): void {
    this.router.navigate(['/feed']);
  }






  goToTopics(): void {
    this.router.navigate(['/topics']);
  }





  
  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}