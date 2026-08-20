package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.UserProfileResponse;
import com.openclassrooms.mddapi.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour la gestion du profil utilisateur. Fournit les endpoints pour :
 *   Récupérer le profil de l'utilisateur connecté
 *   Modifier les informations du profil (nom, email, mot de passe)
 */

@RestController
@RequestMapping("/user")
public class UserController {
	
	

    private final UserService userService;
    
    
    

    //Constructeur
    public UserController(UserService userService) {
	    this.userService = userService;
	}

    
    

//Récuperer le profil
	@GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }
    
    
    
 // Mise à jour du profil
    @PutMapping("/profile")
    
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }
}