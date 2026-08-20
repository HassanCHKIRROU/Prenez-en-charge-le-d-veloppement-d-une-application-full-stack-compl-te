package com.openclassrooms.mddapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    private final String validToken = "valid.jwt.token";
    private final String username = "testuser";

    
    
    @BeforeEach
    void setUp() {
       
    }

    
    
    
    
    @Test
    void shouldSkipAuthForRegister() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/auth/register");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(tokenProvider, never()).validateToken(anyString());
    }

    
    
    
    
    @Test
    void shouldSkipAuthForLogin() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/auth/login");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(tokenProvider, never()).validateToken(anyString());
    }

    
    
    
    
    
    @Test
    void shouldAuthenticateWithValidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/topics");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(tokenProvider.validateToken(validToken)).thenReturn(true);
        when(tokenProvider.getUsernameFromToken(validToken)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(tokenProvider, times(1)).validateToken(validToken);
    }

    
    
    
    
    
    @Test
    void shouldNotAuthenticateWithInvalidToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/topics");
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid.token");
        when(tokenProvider.validateToken("invalid.token")).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    
    
    
    @Test
    void shouldHandleMissingAuthorizationHeader() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/topics");
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(tokenProvider, never()).validateToken(anyString());
    }

    
    
    
    
    
    @Test
    void shouldHandleAuthorizationHeaderWithoutBearer() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/topics");
        when(request.getHeader("Authorization")).thenReturn("Basic credentials");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(tokenProvider, never()).validateToken(anyString());
    }
}