import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { ArticleDetailComponent } from './article-detail.component';
import { ArticleService } from 'src/app/core/services/article.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('ArticleDetailComponent', () => {
  let component: ArticleDetailComponent;
  let fixture: ComponentFixture<ArticleDetailComponent>;
  let articleMock: any;
  let routerMock: any;





  beforeEach(() => {
    //  Ajouter toutes les propriétés requises
    const mockArticle = {
      id: 1,
      title: 'Test',
      content: 'Content',
      author: { id: 1, username: 'testuser', email: 'test@test.com' },
      topic: { id: 1, title: 'Java', description: 'Java programming', subscribed: false },
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
      comments: []
    };
    
    articleMock = {
      getArticle: jasmine.createSpy().and.returnValue(of(mockArticle)),
      addComment: jasmine.createSpy().and.returnValue(of({ id: 1, content: 'comment' }))
    };
    routerMock = { navigate: jasmine.createSpy() };




    TestBed.configureTestingModule({

      imports: [
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        NoopAnimationsModule
      ],

      declarations: [ArticleDetailComponent],

      providers: [
        { provide: ArticleService, useValue: articleMock },
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: { snapshot: { params: { id: 1 } } } }
      ]
    });

    fixture = TestBed.createComponent(ArticleDetailComponent);
    component = fixture.componentInstance;
    component.article = mockArticle as any; 
    fixture.detectChanges();
  });






  it('should create', () => {
    expect(component).toBeTruthy();
  });






  it('should load article', () => {
    component.ngOnInit();
    expect(articleMock.getArticle).toHaveBeenCalledWith(1);
    expect(component.article.id).toBe(1);
  });





  it('should add comment', () => {
    component.commentForm.setValue({ content: 'test' });
    component.onSubmitComment();
    expect(articleMock.addComment).toHaveBeenCalled();
  });




  
  it('should go back', () => {
    component.goBack();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
  });
});