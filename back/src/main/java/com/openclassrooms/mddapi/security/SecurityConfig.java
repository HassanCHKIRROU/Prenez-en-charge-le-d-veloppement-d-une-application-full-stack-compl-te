package com.openclassrooms.mddapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration principale de la sécurité de l'application.
 *
 * Cette classe configure Spring Security afin de gérer :
 *  l'authentification des utilisateurs 
 *  la validation des tokens JWT 
 *  les autorisations d'accès aux différentes ressources 
 *  la gestion des sessions en mode stateless 
 *  le chiffrement des mots de passe avec BCrypt 
 *  la configuration CORS pour le frontend Angular.
 * L'application utilise une authentification basée sur les tokens JWT.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	

	// Filtre chargé d'intercepter les requêtes HTTP et de vérifier la présence et la validité du token JWT.
	
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    
    
    
    /**
     * Configure la chaîne de filtres de sécurité de l'application.
     * Cette configuration permet notamment de :
 
     *   activer la configuration CORS 
     *   désactiver la protection CSRF, adaptée à une API REST utilisant des tokens JWT
     *   désactiver la gestion des sessions HTTP 
     *   autoriser l'accès aux endpoints d'inscription et de connexion 
     *   autoriser l'accès à la documentation Swagger/OpenAPI 
     *   exiger une authentification pour les ressources protégées 
     *   ajouter le filtre JWT avant le filtre d'authentification standard de Spring Security.
     *
     * @param http objet permettant de configurer la sécurité HTTP
     * @return la chaîne de filtres de sécurité configurée
     * @throws Exception si une erreur survient lors de la configuration de la sécurité HTTP
     */
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                		
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/topics/**", "/subscriptions/**", "/user/**", "/articles/**").authenticated()
                        .anyRequest().authenticated()
                		
                )
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    
    
    
    //Fournit le gestionnaire d'authentification utilisé par Spring Security.
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    
    
    //Fournit l'encodeur utilisé pour sécuriser les mots de passe
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  
    
    
    /**
     * Configure les règles CORS utilisées par l'API.
      Le frontend Angular exécuté sur {@code http://localhost:4200} est autorisé à communiquer avec l'API backend.
     *
     * Les méthodes HTTP GET, POST, PUT, DELETE et OPTIONS sont autorisées.
     * Les en-têtes HTTP sont également autorisés et les credentials peuvent être transmis avec les requêtes.
     */
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}