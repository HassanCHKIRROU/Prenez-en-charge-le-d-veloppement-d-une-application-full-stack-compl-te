package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class CommentDTO {
	
	
    
	private Long id;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
    
    
    
    
    //les constructeurs
    public CommentDTO() {}
    
    public CommentDTO(Long id, String content, String authorUsername, LocalDateTime createdAt) {
		
		this.id = id;
		this.content = content;
		this.authorUsername = authorUsername;
		this.createdAt = createdAt;
	}
    
    
    
    
    //Getters & Setters
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthorUsername() {
		return authorUsername;
	}
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
    
}