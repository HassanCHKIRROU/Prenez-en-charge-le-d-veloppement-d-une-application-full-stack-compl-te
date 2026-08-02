import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArticleService } from '../../../core/services/article.service';
import { TopicService } from '../../../core/services/topic.service';
import { TopicDTO } from '../../../shared/models/topic.model';

@Component({
  selector: 'app-article-create',
  templateUrl: './article-create.component.html',
  styleUrls: ['./article-create.component.scss']
})

export class ArticleCreateComponent implements OnInit {


  articleForm: FormGroup;
  topics: TopicDTO[] = [];
  loading: boolean = true;
  submitting: boolean = false;
  error: string = '';



  constructor(
    private fb: FormBuilder,
    private articleService: ArticleService,
    private topicService: TopicService,
    private router: Router
  ) {
    this.articleForm = this.fb.group({
      topicId: ['', [Validators.required]],
      title: ['', [Validators.required, Validators.minLength(3)]],
      content: ['', [Validators.required, Validators.minLength(10)]]
    });
  }




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






    onSubmit(): void {
  if (this.articleForm.invalid || this.submitting) {
    return;
  }

  this.submitting = true;
  this.error = '';

  // Convertir topicId en nombre avant l'envoi
  const formValue = this.articleForm.value;
  const request = {
    topicName: formValue.topicId,  //  Conversion en nombre
    title: formValue.title,
    content: formValue.content
  };

  console.log(' Requête envoyée:', request);  // 

  this.articleService.createArticle(request).subscribe({
    next: (article) => {
      this.submitting = false;
      this.router.navigate(['/article', article.id]);
    },
    error: (err) => {
      this.error = err.error?.message || 'Erreur lors de la création de l\'article';
      this.submitting = false;
    }
  });
}






  cancel(): void {
    this.router.navigate(['/feed']);
  }
}