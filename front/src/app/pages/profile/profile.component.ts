import { AuthService } from './../../core/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../core/services/user.service';
import { UserProfileResponse } from '../../shared/models/user.model';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';
import { SubscriptionService } from 'src/app/core/services/subscription.service';
import { Topic } from 'src/app/shared/models/topic.model';

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
  success: string= '';



  constructor(
    private userService: UserService,
    private authService: AuthService,
    private router : Router,
    private datePipe: DatePipe,
    private subscriptionService: SubscriptionService
   // private topic: Topic
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


  

unsubscribe(topicId: number): void {
    if (!confirm('Voulez-vous vous désabonner de ce thème ?')) {
      return;
    }

    this.subscriptionService.unsubscribe(topicId).subscribe({
      next: () => {
        // Supprimer le thème de la liste des abonnements
        this.userProfile.subscriptions = this.userProfile.subscriptions.filter(
          (topic: Topic) => topic.id !== topicId
        );
        this.success = 'Désabonnement effectué avec succès';
        setTimeout(() => this.success = '', 3000);
      },
      error: (err) => {
        this.error = err.error?.message || 'Erreur lors du désabonnement';
        setTimeout(() => this.error = '', 3000);
      }
    });
  }




}