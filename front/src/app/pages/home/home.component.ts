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




  goToLogin() : void{
    this.router.navigate(['/login']);
  }


  

  goToRegister(): void{
    this.router.navigate(['/register']);
  }
}