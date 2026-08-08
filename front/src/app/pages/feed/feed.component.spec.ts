import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { FeedComponent } from './feed.component';
import { ArticleService } from '../../core/services/article.service';
import { MatIconModule } from '@angular/material/icon';

describe('FeedComponent', () => {
  let component: FeedComponent;
  let fixture: ComponentFixture<FeedComponent>;
  let articleMock: any;
  let routerMock: any;





  beforeEach(() => {
    articleMock = { getFeed: jasmine.createSpy().and.returnValue(of([])) };
    routerMock = { navigate: jasmine.createSpy() };

    TestBed.configureTestingModule({
      declarations: [FeedComponent],
      providers: [
        { provide: ArticleService, useValue: articleMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    fixture = TestBed.createComponent(FeedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });





  it('should create', () => {
    expect(component).toBeTruthy();
  });






  it('should load feed on init', () => {
    component.ngOnInit();
    expect(articleMock.getFeed).toHaveBeenCalled();
  });






  it('should navigate to article', () => {
    component.goToArticle(1);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/article', 1]);
  });







  it('should navigate to create article', () => {
    component.goToCreateArticle();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/article/create']);
  });




  
  it('should change sort', () => {
    component.changeSort('asc');
    expect(component.sortOrder).toBe('asc');
    expect(articleMock.getFeed).toHaveBeenCalledWith('asc');
  });
});