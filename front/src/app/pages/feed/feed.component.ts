import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from '../../core/services/article.service';
import { ArticleSummary } from '../../shared/models/article.model';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})

export class FeedComponent implements OnInit {

  
  articles: ArticleSummary[] = [];
  loading: boolean = true;
  error: string = '';
  sortOrder: string = 'desc';




  constructor(
    private articleService: ArticleService,
    private router: Router
  ) {}




  ngOnInit(): void {
    this.loadFeed();
  }




  loadFeed(): void {
    this.loading = true;
    this.articleService.getFeed(this.sortOrder).subscribe({
      next: (data) => {
        this.articles = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement du fil d\'actualité';
        this.loading = false;
      }
    });
  }




  changeSort(order: string): void {
    this.sortOrder = order;
    this.loadFeed();
  }




  goToArticle(id: number): void {
    this.router.navigate(['/article', id]);
  }


  

  goToCreateArticle(): void {
    console.log('Navigation vers /article/create');
    this.router.navigate(['/article/create']);
  }
}