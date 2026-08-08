package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class TopicDTO {
	
	
    
	private Long id;
    private String title;
    private String description;
    private Boolean subscribed;
    
    
    
    
    //Constructors
    public TopicDTO() {}
    
    public TopicDTO(Long id, String title, String description, Boolean subscribed) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.subscribed = subscribed;
	}
    
    
    
    
    //Getters & Setters
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getSubscribed() {
		return subscribed;
	}
	public void setSubscribed(Boolean subscribed) {
		this.subscribed = subscribed;
	}
    
    
    
}