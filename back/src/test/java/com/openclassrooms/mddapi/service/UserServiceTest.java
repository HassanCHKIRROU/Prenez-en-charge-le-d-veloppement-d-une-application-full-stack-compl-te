package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.UserProfileResponse;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    private User user;
    private Topic topic;
    private Subscription subscription;
    private UpdateProfileRequest updateRequest;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        topic = new Topic();
        topic.setId(1L);
        topic.setTitle("Java");
        topic.setDescription("Java programming");

        subscription = new Subscription();
        subscription.setId(1L);
        subscription.setUser(user);
        subscription.setTopic(topic);

        updateRequest = new UpdateProfileRequest();
        updateRequest.setUsername("newusername");
        updateRequest.setEmail("new@example.com");
        updateRequest.setPassword("NewTest1234!");
    }

    @Test
    void shouldGetCurrentUserProfileSuccessfully() {
        // Given
        List<Subscription> subscriptions = List.of(subscription);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(subscriptionRepository.findByUserId(1L)).thenReturn(subscriptions);

        // When
        UserProfileResponse response = userService.getCurrentUserProfile();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getSubscriptions()).isNotEmpty();
        assertThat(response.getSubscriptions().get(0).getTitle()).isEqualTo("Java");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.getCurrentUserProfile())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Utilisateur non trouvé");
    }

    @Test
    void shouldUpdateProfileSuccessfully() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newusername")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(passwordEncoder.encode("NewTest1234!")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(subscriptionRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        // When
        UserProfileResponse response = userService.updateProfile(updateRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("newusername");
        assertThat(response.getEmail()).isEqualTo("new@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newusername")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.updateProfile(updateRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Ce nom d'utilisateur est déjà utilisé");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newusername")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.updateProfile(updateRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cet email est déjà utilisé");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldUpdateProfileWithoutPassword() {
        // Given
        updateRequest.setPassword(null);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newusername")).thenReturn(false);
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(subscriptionRepository.findByUserId(1L)).thenReturn(new ArrayList<>());

        // When
        UserProfileResponse response = userService.updateProfile(updateRequest);

        // Then
        assertThat(response).isNotNull();
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }
}