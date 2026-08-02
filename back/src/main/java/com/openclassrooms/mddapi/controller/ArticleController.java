package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleSummaryDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.request.ArticleRequest;
import com.openclassrooms.mddapi.dto.request.CommentRequest;
import com.openclassrooms.mddapi.dto.response.ArticleResponse;
import com.openclassrooms.mddapi.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")

public class ArticleController {
	
	

    private final ArticleService articleService;
    
    

//Constructeur pour injecter la dependance service
	public ArticleController(ArticleService articleService) {

		this.articleService = articleService;
	}



	
	
	
	@GetMapping("/feed")
    public ResponseEntity<List<ArticleSummaryDTO>> getFeed(@RequestParam(defaultValue = "desc") String sort) {
        return ResponseEntity.ok(articleService.getFeed(sort));
    }

    
    
   
    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody ArticleRequest request) {
    	 return ResponseEntity.ok(articleService.createArticle(request));
    }
    
    
    

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }
    
    
    
    

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok(articleService.addComment(id, request));
    }
}