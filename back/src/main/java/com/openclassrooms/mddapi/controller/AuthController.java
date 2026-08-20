package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.request.LoginRequest;
import com.openclassrooms.mddapi.dto.request.RegisterRequest;
import com.openclassrooms.mddapi.dto.response.AuthResponse;
import com.openclassrooms.mddapi.security.JwtAuthenticationFilter;
import com.openclassrooms.mddapi.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour l'authentification des utilisateurs. Fournit les endpoints pour :
 *   L'inscription d'un nouvel utilisateur
 *   La connexion d'un utilisateur existant
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    
    
    
    //Constructeur pour injecter le service
    public AuthController(AuthService authService) {
		this.authService = authService;
	}





	//@Autowired
  //  private JwtAuthenticationFilter jwtAuthenticationFilter;
    
  
    //* Inscription d'un nouvel utilisateur. Crée un compte utilisateur avec un nom d'utilisateur, un email et un mot de passe. 
    
	@PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    
	
	// * Connexion d'un utilisateur.Authentifie un utilisateur avec son email ou son nom d'utilisateur.
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}