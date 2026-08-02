package com.openclassrooms.mddapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.service.SubscriptionService;

//import com.openclassrooms.mddapi.service.SubscriptionService;



@RestController
@RequestMapping("/subscriptions")
//@RequiredArgsConstructor
public class SubscriptionController {

	
	
    private final SubscriptionService subscriptionService;
    
    

   
    //Constructeur
    public SubscriptionController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}



    
    
	@PostMapping("/{topicId}")
    public ResponseEntity<Void> subscribe(@PathVariable Long topicId) {
        subscriptionService.subscribe(topicId);
        return ResponseEntity.ok().build();
    }

    
	
	
    
    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long topicId) {
        subscriptionService.unsubscribe(topicId);
        return ResponseEntity.ok().build();
    }
}