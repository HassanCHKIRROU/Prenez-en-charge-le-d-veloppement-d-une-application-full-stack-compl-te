package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class CommentRequest {
	
	
	
    @NotBlank(message = "Le contenu du commentaire est requis")
    private String content;
    
    
    
    
    
    //Constructors
    
    public CommentRequest() {}

	public CommentRequest(@NotBlank(message = "Le contenu du commentaire est requis") String content) {
		
		this.content = content;
	}

	
	
	//getter & setter
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
    
}