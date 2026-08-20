package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface de repository pour l'entité Comment. Fournit les méthodes d'accès aux données pour les commentaires.
 * Hérite de JpaRepository pour les opérations CRUD de base.
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	
    List<Comment> findByArticleIdOrderByCreatedAtAsc(Long articleId);
}