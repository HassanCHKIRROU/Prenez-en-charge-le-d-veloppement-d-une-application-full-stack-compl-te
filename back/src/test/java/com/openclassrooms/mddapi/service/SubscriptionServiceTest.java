package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private User user;
    private Topic topic;

    
    
    
    
    
    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        topic = new Topic();
        topic.setId(1L);
        topic.setTitle("Java");
    }

    
    
    
    
    
    @Test
    void shouldSubscribe() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(subscriptionRepository.existsByUserIdAndTopicId(1L, 1L)).thenReturn(false);

        subscriptionService.subscribe(1L);

        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    
    
    
    
    
    @Test
    void shouldThrowExceptionWhenAlreadySubscribed() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(subscriptionRepository.existsByUserIdAndTopicId(1L, 1L)).thenReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            subscriptionService.subscribe(1L);
        });

        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    
    
    
    
    
    @Test
    void shouldUnsubscribe() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(subscriptionRepository.existsByUserIdAndTopicId(1L, 1L)).thenReturn(true);

        subscriptionService.unsubscribe(1L);

        verify(subscriptionRepository, times(1)).deleteByUserIdAndTopicId(1L, 1L);
    }

    
    
    
    
    
    @Test
    void shouldThrowExceptionWhenNotSubscribed() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(subscriptionRepository.existsByUserIdAndTopicId(1L, 1L)).thenReturn(false);

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            subscriptionService.unsubscribe(1L);
        });

        verify(subscriptionRepository, never()).deleteByUserIdAndTopicId(anyLong(), anyLong());
    }

    
    
    
    
    
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            subscriptionService.subscribe(1L);
        });
    }

    
    
    
    
    
    @Test
    void shouldThrowExceptionWhenTopicNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(topicRepository.findById(99L)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            subscriptionService.subscribe(99L);
        });
    }
}