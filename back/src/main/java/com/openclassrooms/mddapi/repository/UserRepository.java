package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface de repository pour l'entité User. Fournit les méthodes d'accès aux données pour les utilisateurs.
 * Hérite de JpaRepository pour les opérations CRUD de base.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmailOrUsername(String email, String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
}