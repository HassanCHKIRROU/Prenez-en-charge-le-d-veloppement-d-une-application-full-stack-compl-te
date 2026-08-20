package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Topic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repository pour l'entité Topic. Fournit les méthodes d'accès aux données pour les thèmes.
 * Hérite de JpaRepository pour les opérations CRUD de base.
 */

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
	
	Optional <Topic> findByTitle(String title);
}