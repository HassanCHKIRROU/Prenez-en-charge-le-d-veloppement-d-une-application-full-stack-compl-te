package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interface de repository pour l'entité Article.
 * Fournit les méthodes d'accès aux données pour les articles.
 */

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	
	
	 /**
     * Récupère les articles des thèmes auxquels un utilisateur est abonné.
     * Requête JPQL qui sélectionne les articles dont le thème fait partie
     * des abonnements de l'utilisateur.
     */
    
    @Query("SELECT a FROM Article a WHERE a.topic.id IN (SELECT s.topic.id FROM Subscription s WHERE s.user.id = :userId)")
    List<Article> findArticlesBySubscribedTopics(@Param("userId") Long userId);
    
    List<Article> findAllByOrderByCreatedAtDesc();
    List<Article> findAllByOrderByCreatedAtAsc();
    List<Article> findByAuthorId(Long authorId);
}