package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implémentation du service de gestion des informations d'authentification des utilisateurs pour Spring Security.
 * Cette classe permet à Spring Security de récupérer les informations
 *  d'un utilisateur à partir de son adresse e-mail ou de son nom d'utilisateur.
 *
 * <p>Les informations récupérées depuis la base de données sont ensuite
 * converties en objet {@link UserDetails}, utilisé par Spring Security
 * lors du processus d'authentification.
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	

    @Autowired
    private UserRepository userRepository;

    
    
    
    /**
     * Recherche un utilisateur à partir de son adresse e-mail ou de son nom d'utilisateur.
     *
     * Si l'utilisateur existe, ses informations d'authentification sontconverties en objet {@link UserDetails}.
     *  L'utilisateur reçoit également le rôle {@code ROLE_USER} utilisé par Spring Security pour gérer
     * ses autorisations.
     *
     * Si aucun utilisateur correspondant au nom fourni n'est trouvé, une UsernameNotFoundException est levée.
     *
     * @param username adresse e-mail ou nom d'utilisateur recherché
     * @return les informations de l'utilisateur sous forme de UserDetails
     * @throws UsernameNotFoundException si aucun utilisateur correspondant n'est trouvé
     */
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}