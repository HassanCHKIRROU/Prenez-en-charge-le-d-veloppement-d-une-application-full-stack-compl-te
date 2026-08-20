package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.request.LoginRequest;
import com.openclassrooms.mddapi.dto.request.RegisterRequest;
import com.openclassrooms.mddapi.dto.response.AuthResponse;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service responsable de la gestion de l'authentification des utilisateurs.
 *Cette classe contient la logique métier associée à l'inscription et à la connexion des utilisateurs.
 *
 * Elle utilise Spring Security pour authentifier les utilisateurs,
 * un encodeur BCrypt pour sécuriser les mots de passe et un fournisseur
 * de tokens JWT pour générer les tokens utilisés lors des requêtes authentifiées.
 */

@Service
public class AuthService {
	
	
	

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    
    
    
    
    
    //Constructeur
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }
    
    
    
    
    
    
    
    /**
     * Inscrit un nouvel utilisateur dans l'application.
     * Avant de créer l'utilisateur, la méthode vérifie que son adresse e-mail et son nom d'utilisateur ne sont pas déjà utilisés.
       Le mot de passe est encodé avant d'être enregistré en base
     *  Une authentification est ensuite effectuée afin de générer un token JWT pour le nouvel utilisateur
     *
     * @param request données nécessaires à l'inscription de l'utilisateur
     * @return les informations d'authentification de l'utilisateur, son token JWT
     * @throws RuntimeException si l'adresse e-mail ou le nom d'utilisateur est déjà utilisé
     */

    public AuthResponse register(RegisterRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Vérifier si le nom d'utilisateur existe déjà
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà utilisé");
        }

        //  Créer l'utilisateur 
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        // Générer le token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        // Retourner la réponse 
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }

    
    
    
    
    
    
    
    
    /**
     * Authentifie un utilisateur existant.
     *
     * La méthode utilise l'adresse e-mail ou le nom d'utilisateur fourni dans la requête pour effectuer l'authentification.
     * Si celle-ci réussit, un token JWT est généré et ajouté à la réponse.
     * Les informations de l'utilisateur sont ensuite récupérées depuis la base de données afin de construire la réponse
     * d'authentification.
     *
     * @param request données contenant l'identifiant et le mot de passe de l'utilisateur
     * @return les informations d'authentification de l'utilisateur, notamment son token JWT
     * @throws RuntimeException si l'utilisateur correspondant aux identifiants n'est pas trouvé
     */
    
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByEmailOrUsername(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        //  Retourner la réponse 
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }
}