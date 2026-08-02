import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../core/services/topic.service';
import { SubscriptionService } from '../../core/services/subscription.service';
import { TopicDTO } from '../../shared/models/topic.model';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})

export class TopicsComponent implements OnInit {


  topics: TopicDTO[] = [];
  loading: boolean = true;
  error: string = '';




  constructor(
    private topicService: TopicService,
    private subscriptionService: SubscriptionService
  ) {}





  ngOnInit(): void {
    this.loadTopics();
  }





  loadTopics(): void {
    this.loading = true;
    this.topicService.getAllTopics().subscribe({
      next: (data) => {
        this.topics = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement des thèmes';
        this.loading = false;
      }
    });
  }





  subscribe(topicId: number): void {
    this.subscriptionService.subscribe(topicId).subscribe({
      next: () => {
        // Mettre à jour le statut local
        const topic = this.topics.find(t => t.id === topicId);
        if (topic) {
          topic.subscribed = true;
        }
      },
      error: (err) => {
        this.error = err.error?.message || 'Erreur lors de l\'abonnement';
      }
    });
  }





  unsubscribe(topicId: number): void {
    this.subscriptionService.unsubscribe(topicId).subscribe({
      next: () => {
        // Mettre à jour le statut local
        const topic = this.topics.find(t => t.id === topicId);
        if (topic) {
          topic.subscribed = false;
        }
      },
      error: (err) => {
        this.error = err.error?.message || 'Erreur lors du désabonnement';
      }
    });
  }




  
  onSubscriptionToggle(topic: TopicDTO): void {
    if (topic.subscribed) {
      this.unsubscribe(topic.id);
    } else {
      this.subscribe(topic.id);
    }
  }
}