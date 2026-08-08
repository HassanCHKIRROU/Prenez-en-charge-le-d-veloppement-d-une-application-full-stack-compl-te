package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider tokenProvider;
    private String secret = "zJ5nY8tR3mX9cL2wQ7fD4vK6pS1hU8oAzJ5nY8tR3mX9cL2wQ7fD4vK6pS1hU8oA";
    private int expiration = 86400000;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider();
        // Utiliser reflection pour setter les valeurs privées
        try {
            var secretField = JwtTokenProvider.class.getDeclaredField("jwtSecret");
            secretField.setAccessible(true);
            secretField.set(tokenProvider, secret);

            var expirationField = JwtTokenProvider.class.getDeclaredField("jwtExpiration");
            expirationField.setAccessible(true);
            expirationField.set(tokenProvider, expiration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldGenerateToken() {
        // Given
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // When
        String token = tokenProvider.generateToken(authentication);

        // Then
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
    }

    @Test
    void shouldValidateToken() {
        // Given
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = tokenProvider.generateToken(authentication);

        // When
        boolean isValid = tokenProvider.validateToken(token);

        // Then
        assertThat(isValid).isTrue();
    }

    @Test
    void shouldGetUsernameFromToken() {
        // Given
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String token = tokenProvider.generateToken(authentication);

        // When
        String username = tokenProvider.getUsernameFromToken(token);

        // Then
        assertThat(username).isEqualTo("testuser");
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        // Given
        String invalidToken = "invalid.token.value";

        // When
        boolean isValid = tokenProvider.validateToken(invalidToken);

        // Then
        assertThat(isValid).isFalse();
    }
}