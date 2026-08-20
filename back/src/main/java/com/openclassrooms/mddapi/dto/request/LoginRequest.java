package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;



public class LoginRequest {
	
	
    @NotBlank(message = "Email ou nom d'utilisateur est requis")
    private String usernameOrEmail;
    

    @NotBlank(message = "Mot de passe est requis")
    private String password;
    
    
    
    
    
//Les constructeurs

    public LoginRequest() {}
    
	public LoginRequest(@NotBlank(message = "Email ou nom d'utilisateur est requis") String usernameOrEmail,
			@NotBlank(message = "Mot de passe est requis") String password) {
		
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}


	
	
	//les getters et les setters
	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}


	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}