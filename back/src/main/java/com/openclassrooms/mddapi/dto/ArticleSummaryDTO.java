package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class ArticleSummaryDTO {
	
	
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private String topicTitle;
    private LocalDateTime createdAt;
    
    
    
    
    
    // les constructeurs
	public ArticleSummaryDTO() {}
	
	
	public ArticleSummaryDTO(Long id, String title, String content, String authorUsername, String topicTitle,
			LocalDateTime createdAt) {
		
		this.id = id;
		this.title = title;
		this.content = content;
		this.authorUsername = authorUsername;
		this.topicTitle = topicTitle;
		this.createdAt = createdAt;
	}
	
	
	
	//les getters et setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getAuthorUsername() {
		return authorUsername;
	}
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}
	public String getTopicTitle() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}