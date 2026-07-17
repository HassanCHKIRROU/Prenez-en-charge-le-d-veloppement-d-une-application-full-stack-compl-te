import { AuthService } from './../../core/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { UserProfileResponse } from '../../shared/models/user.model';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})

export class ProfileComponent implements OnInit {


  userProfile!: UserProfileResponse;
  loading: boolean = true;
  error: string = '';
  formattedCreatedAt: string ='';



  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router : Router,
    private datePipe: DatePipe
) {}




  ngOnInit(): void {
    this.loadProfile();
  }



  
  loadProfile(): void {
    this.loading = true;
    this.userService.getProfile().subscribe({
      next: (data) => {
        this.userProfile = data;
        this.loading = false;
        this.formattedCreatedAt = this.datePipe.transform(data.createdAt, 'dd/MM/yyyy') || '';
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement du profil';
        this.loading = false;
      }
    });
  }




  logout(): void{
    this.authService.logout();
    this.router.navigate(['/login']);
  }



  unsubscribe(topicId: number): void{
    if(confirm('Voulez- vous désabonner de ce thème?')){
        console.log('Se désabonner de topic: ' ,topicId)
    }
  }



}