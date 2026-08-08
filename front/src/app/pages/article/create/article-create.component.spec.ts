import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ArticleCreateComponent } from './article-create.component';
import { ArticleService } from 'src/app/core/services/article.service';
import { TopicService } from 'src/app/core/services/topic.service';
import { MatIconModule } from '@angular/material/icon';



describe('ArticleCreateComponent', () => {
  let component: ArticleCreateComponent;
  let fixture: ComponentFixture<ArticleCreateComponent>;
  let articleMock: any;
  let topicMock: any;
  let routerMock: any;




  beforeEach(() => {
    articleMock = {
      createArticle: jasmine.createSpy().and.returnValue(of({ id: 1 }))
    };
    topicMock = {
      getAllTopics: jasmine.createSpy().and.returnValue(of([{ id: 1, title: 'Java' }]))
    };
    routerMock = { navigate: jasmine.createSpy() };




    TestBed.configureTestingModule({

      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        NoopAnimationsModule
      ],


      declarations: [ArticleCreateComponent],
      providers: [
        { provide: ArticleService, useValue: articleMock },
        { provide: TopicService, useValue: topicMock },
        { provide: Router, useValue: routerMock }
      ]
    });



    fixture = TestBed.createComponent(ArticleCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });





  it('should create', () => {
    expect(component).toBeTruthy();
  });





  it('should have a form with 3 controls', () => {
    expect(component.articleForm.contains('topicId')).toBeTrue();
    expect(component.articleForm.contains('title')).toBeTrue();
    expect(component.articleForm.contains('content')).toBeTrue();
  });





  it('should load topics on init', () => {
    component.ngOnInit();
    expect(topicMock.getAllTopics).toHaveBeenCalled();
    expect(component.topics.length).toBe(1);
    expect(component.topics[0].title).toBe('Java');
    expect(component.loading).toBe(false);
  });







  it('should show error when loading topics fails', () => {
    topicMock.getAllTopics.and.returnValue(throwError(() => new Error('Error')));
    component.ngOnInit();
    expect(component.error).toBe('Erreur lors du chargement des thèmes');
    expect(component.loading).toBe(false);
  });





  it('should have onSubmit method defined', () => {
    expect(component.onSubmit).toBeDefined();
  });







  it('should not submit if form invalid', () => {
    component.articleForm.setValue({ topicId: '', title: '', content: '' });
    component.onSubmit();
    expect(articleMock.createArticle).not.toHaveBeenCalled();
  });







  it('should not submit if already submitting', () => {
    component.submitting = true;
    component.onSubmit();
    expect(articleMock.createArticle).not.toHaveBeenCalled();
  });







  it('should cancel and navigate to feed', () => {
    component.goBack();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
  });







   
  it('should create article and navigate to article page', () => {
    component.articleForm.setValue({
      topicId: 'Java',
      title: 'Mon article',
      content: 'Contenu très long pour respecter la validation'
    });

    articleMock.createArticle.and.returnValue(of({ id: 15 }));

    component.onSubmit();

    expect(articleMock.createArticle).toHaveBeenCalledWith({
      topicName: 'Java',
      title: 'Mon article',
      content: 'Contenu très long pour respecter la validation'
    });

    expect(component.submitting).toBeFalse();
    expect(component.error).toBe('');
    expect(routerMock.navigate).toHaveBeenCalledWith(['/article', 15]);
  });

  






  
  it('should display error when article creation fails', () => {
    component.articleForm.setValue({
      topicId: 'Java',
      title: 'Mon article',
      content: 'Contenu très long pour respecter la validation'
    });

    articleMock.createArticle.and.returnValue(
      throwError(() => ({
        error: {
          message: 'Erreur serveur'
        }
      }))
    );

    component.onSubmit();

    expect(articleMock.createArticle).toHaveBeenCalled();

    expect(component.error).toBe('Erreur serveur');
    expect(component.submitting).toBeFalse();
    expect(routerMock.navigate).not.toHaveBeenCalled();
  });

  

});