import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { TopicsComponent } from './topics.component';
import { TopicService } from '../../core/services/topic.service';
import { SubscriptionService } from '../../core/services/subscription.service';
import { MatIconModule } from '@angular/material/icon';

describe('TopicsComponent', () => {
  let component: TopicsComponent;
  let fixture: ComponentFixture<TopicsComponent>;
  let topicMock: any;
  let subMock: any;




  beforeEach(() => {
    topicMock = { 
      getAllTopics: jasmine.createSpy().and.returnValue(of([{ id: 1, title: 'Java', subscribed: false }])) 
    };
    subMock = { 
      subscribe: jasmine.createSpy().and.returnValue(of(null)) 
    };

    TestBed.configureTestingModule({
      declarations: [TopicsComponent],
      providers: [
        { provide: TopicService, useValue: topicMock },
        { provide: SubscriptionService, useValue: subMock }
      ]
    });

    fixture = TestBed.createComponent(TopicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });





  it('should create', () => {
    expect(component).toBeTruthy();
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






  it('should subscribe to a topic', () => {
    component.topics = [{ id: 1, title: 'Java', subscribed: false }] as any;
    component.subscribe(1);
    expect(subMock.subscribe).toHaveBeenCalledWith(1);
    expect(component.topics[0].subscribed).toBe(true);
  });






  it('should handle subscribe error', () => {
    subMock.subscribe.and.returnValue(throwError(() => ({ error: { message: 'Error' } })));
    component.topics = [{ id: 1, title: 'Java', subscribed: false }] as any;
    component.subscribe(1);
    expect(component.error).toBe('Error');
  });






  it('should unsubscribe from a topic', () => {
    subMock.unsubscribe = jasmine.createSpy().and.returnValue(of(null));
    component.topics = [{ id: 1, title: 'Java', subscribed: true }] as any;
    component.unsubscribe(1);
    expect(subMock.unsubscribe).toHaveBeenCalledWith(1);
    expect(component.topics[0].subscribed).toBe(false);
  });






  it('should toggle subscription', () => {
    subMock.unsubscribe = jasmine.createSpy().and.returnValue(of(null));
    const topic = { id: 1, title: 'Java', subscribed: true } as any;
    component.topics = [topic];
    component.onSubscriptionToggle(topic);
    expect(subMock.unsubscribe).toHaveBeenCalledWith(1);
    expect(component.topics[0].subscribed).toBe(false);
  });





  

  it('should toggle subscription (subscribe)', () => {
    const topic = { id: 1, title: 'Java', subscribed: false } as any;
    component.topics = [topic];
    component.onSubscriptionToggle(topic);
    expect(subMock.subscribe).toHaveBeenCalledWith(1);
    expect(component.topics[0].subscribed).toBe(true);
  });
});