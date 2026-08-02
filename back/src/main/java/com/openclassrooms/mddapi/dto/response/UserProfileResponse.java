package com.openclassrooms.mddapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.openclassrooms.mddapi.dto.TopicDTO;


public class UserProfileResponse {
	
	
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TopicDTO> subscriptions;
    
    
    
    
    //Constructors
    public UserProfileResponse() {}
    
	public UserProfileResponse(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt,
			List<TopicDTO> subscriptions) {
	
		this.id = id;
		this.username = username;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.subscriptions = subscriptions;
	}
	
	
	
	
	//Getters & Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<TopicDTO> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List<TopicDTO> subscriptions) {
		this.subscriptions = subscriptions;
	}
    
    
}