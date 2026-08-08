package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private TopicService topicService;

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
        topic.setDescription("Java programming");
    }

    @Test
    void shouldGetAllTopics() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(topicRepository.findAll()).thenReturn(List.of(topic));
        when(subscriptionRepository.existsByUserIdAndTopicId(1L, 1L)).thenReturn(false);

        List<TopicDTO> topics = topicService.getAllTopics();

        assertThat(topics).isNotEmpty();
        assertThat(topics.size()).isEqualTo(1);
        assertThat(topics.get(0).getTitle()).isEqualTo("Java");
        assertThat(topics.get(0).getSubscribed()).isFalse();
    }

    @Test
    void shouldReturnSubscribedTrue() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(topicRepository.findAll()).thenReturn(List.of(topic));
        when(subscriptionRepository.existsByUserIdAndTopicId(1L, 1L)).thenReturn(true);

        List<TopicDTO> topics = topicService.getAllTopics();

        assertThat(topics.get(0).getSubscribed()).isTrue();
    }

    @Test
    void shouldReturnEmptyListWhenNoTopics() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(topicRepository.findAll()).thenReturn(List.of());

        List<TopicDTO> topics = topicService.getAllTopics();

        assertThat(topics).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            topicService.getAllTopics();
        });
    }
}