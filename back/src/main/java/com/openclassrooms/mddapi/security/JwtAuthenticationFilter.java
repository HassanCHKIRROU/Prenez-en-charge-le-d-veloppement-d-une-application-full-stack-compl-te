package com.openclassrooms.mddapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre d'authentification JWT.
 * 
 * Intercepte chaque requête HTTP pour vérifier la présence d'un token JWT dans l'en-tête Authorization. 
 * Si le token est valide, l'utilisateur est authentifié et le contexte de sécurité est mis à jour.
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	
    @Autowired
    private JwtTokenProvider tokenProvider;

  
    @Autowired
    private UserDetailsService userDetailsService;

 
    
    
    
    /**
     * Filtre les requêtes HTTP pour authentifier l'utilisateur via JWT.
     * Les requêtes vers /auth/** sont ignorées (pas de token requis).
     * Pour les autres requêtes, le token est extrait et validé.
     */
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

  
        String path = request.getRequestURI();
        if(path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        
        String token = getJwtFromRequest(request);

        if (token != null && tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        
        filterChain.doFilter(request, response);
    }

    
    
   
    
    
    
    /**
     * Extrait le token JWT de l'en-tête Authorization de la requête.
     * L'entête doit être au format : "Bearer {token}".
     */
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
}