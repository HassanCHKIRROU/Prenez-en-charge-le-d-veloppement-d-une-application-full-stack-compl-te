package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.service.TopicService;



@RestController
@RequestMapping("/topics")
public class TopicController {
	
	private final TopicService topicService;
	
	
	
	
	//Constructeur
	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}



    //Récuperer tous les thèmes pour un utilisateur connecté

	@GetMapping
	public ResponseEntity<List<TopicDTO>> getAllTopics(){
		return ResponseEntity.ok(topicService.getAllTopics());
	}

}
