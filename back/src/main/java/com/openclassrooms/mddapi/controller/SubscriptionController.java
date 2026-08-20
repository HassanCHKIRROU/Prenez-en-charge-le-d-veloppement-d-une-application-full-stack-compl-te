package com.openclassrooms.mddapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.service.SubscriptionService;






@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

	
	
    private final SubscriptionService subscriptionService;
    
    

   
    //Constructeur
    public SubscriptionController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}



    //S'abonne à un thème pour l'utilisateur connecté
    
	@PostMapping("/{topicId}")
	 public ResponseEntity<Void> subscribe(@PathVariable Long topicId) {
        subscriptionService.subscribe(topicId);
        return ResponseEntity.ok().build();
    }

    
	
	
    //Se désabonne d'un thème pour l'utilisateur connecté.
	
    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long topicId) {
        subscriptionService.unsubscribe(topicId);
        return ResponseEntity.ok().build();
    }
}