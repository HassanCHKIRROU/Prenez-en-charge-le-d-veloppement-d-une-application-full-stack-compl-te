import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArticleService } from '../../../core/services/article.service';
import { ArticleResponse } from '../../../shared/models/article.model';
import { CommentRequest } from '../../../shared/models/comment.model';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss']
})

export class ArticleDetailComponent implements OnInit {


  article!: ArticleResponse;
  loading: boolean = true;
  error: string = '';
  commentForm: FormGroup;
  submitting: boolean = false;



  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private articleService: ArticleService,
    private fb: FormBuilder
  ) {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(1)]]
    });
  }





  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.loadArticle(id);
    }
  }





  loadArticle(id: number): void {
    this.loading = true;
    this.articleService.getArticle(id).subscribe({
      next: (data) => {
        this.article = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement de l\'article';
        this.loading = false;
      }
    });
  }







  onSubmitComment(): void {
    if (this.commentForm.invalid || this.submitting) {
      return;
    }

    this.submitting = true;
    const request: CommentRequest = {
      content: this.commentForm.value.content
    };

    this.articleService.addComment(this.article.id, request).subscribe({
      next: (comment) => {
        if (this.article.comments) {
          this.article.comments.push(comment);
        } else {
          this.article.comments = [comment];
        }
        this.commentForm.reset();
        this.submitting = false;
      },
      error: (err) => {
        this.error = err.error?.message || 'Erreur lors de l\'ajout du commentaire';
        this.submitting = false;
      }
    });
  }





  
  goBack(): void {
    this.router.navigate(['/feed']);
  }
}