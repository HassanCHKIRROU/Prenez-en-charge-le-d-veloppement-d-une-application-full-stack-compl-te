package com.openclassrooms.mddapi.dto.response;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleResponse {
	
	
	
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
    private TopicDTO topic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentDTO> comments;
    
    
    
    
    // Les constructeurs
    public ArticleResponse() {}
    
	public ArticleResponse(Long id, String title, String content, UserDTO author, TopicDTO topic,
			LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentDTO> comments) {
		
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.topic = topic;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.comments = comments;
	}
	
	
	//getters & setters
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
	public UserDTO getAuthor() {
		return author;
	}
	public void setAuthor(UserDTO author) {
		this.author = author;
	}
	public TopicDTO getTopic() {
		return topic;
	}
	public void setTopic(TopicDTO topic) {
		this.topic = topic;
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
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
    
    
    
}