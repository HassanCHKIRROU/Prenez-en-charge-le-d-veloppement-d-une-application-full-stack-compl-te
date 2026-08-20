package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleSummaryDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.request.ArticleRequest;
import com.openclassrooms.mddapi.dto.request.CommentRequest;
import com.openclassrooms.mddapi.dto.response.ArticleResponse;
import com.openclassrooms.mddapi.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des articles. Fournit les endpoints pour :
 *   Récupérer le fil d'actualité
 *   Créer un article
 *   Consulter un article
 *   Ajouter un commentaire à un article 
 */


@RestController
@RequestMapping("/articles")
@Tag(name = "Articles", description = "Gestion des articles")
@SecurityRequirement(name = "BearerAuth")

public class ArticleController {
	
	

    private final ArticleService articleService;
    
    

//Constructeur pour injecter la dependance service
	public ArticleController(ArticleService articleService) {

		this.articleService = articleService;
	}



	
	
	//Récupère le fil d'actualité de l'utilisateur connecté.
	
	@GetMapping("/feed")
	public ResponseEntity<List<ArticleSummaryDTO>> getFeed(@RequestParam(defaultValue = "desc") String sort) {
        return ResponseEntity.ok(articleService.getFeed(sort));
    }

	
	
    
    
   //Crée un nouvel article.
	
    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest request) {
    	 return ResponseEntity.ok(articleService.createArticle(request));
    }
    
    
    

    //Récupère un article par son identifiant
    
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }
    
    
    
    

    //Ajoute un commentaire à un article.
    
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok(articleService.addComment(id, request));
    }
}