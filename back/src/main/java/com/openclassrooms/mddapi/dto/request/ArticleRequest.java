package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class ArticleRequest {
	
	
	
    @NotNull(message = "Le nom du thème est requis")
    private String topicName;

    
    
    @NotBlank(message = "Le titre est requis")
    private String title;

    
    
    @NotBlank(message = "Le contenu est requis")
    private String content;

    
    
    

    //Les constructeurs
    public ArticleRequest() {}

	public ArticleRequest(@NotNull(message = "Le nom du thème est requis") String topicName,
			@NotBlank(message = "Le titre est requis") String title,
			@NotBlank(message = "Le contenu est requis") String content) {
	
		this.topicName = topicName;
		this.title = title;
		this.content = content;
	}


	
	
//Getters & Setters
	public String getTopicName() {
		return topicName;
	}



	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}
    
    
    
}