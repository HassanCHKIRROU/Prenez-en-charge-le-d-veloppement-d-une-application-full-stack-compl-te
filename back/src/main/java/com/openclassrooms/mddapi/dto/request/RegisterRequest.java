package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class RegisterRequest {
	

    @NotBlank(message = "Nom d'utilisateur est requis")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit faire entre 3 et 50 caractères")
    private String username;
    

    @NotBlank(message = "Email est requis")
    @Email(message = "Format d'email invalide")
    private String email;
    
    

    @NotBlank(message = "Mot de passe est requis")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "Le mot de passe doit contenir au moins 1 chiffre, 1 minuscule, 1 majuscule et 1 caractère spécial"
    )
    
    
    
    private String password;


    
    
    
    //Constructors
    
   public RegisterRequest() {} 

	public RegisterRequest(
			@NotBlank(message = "Nom d'utilisateur est requis") @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit faire entre 3 et 50 caractères") String username,
			@NotBlank(message = "Email est requis") @Email(message = "Format d'email invalide") String email,
			@NotBlank(message = "Mot de passe est requis") @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Le mot de passe doit contenir au moins 1 chiffre, 1 minuscule, 1 majuscule et 1 caractère spécial") String password) {
	
		this.username = username;
		this.email = email;
		this.password = password;
	}


	
	
	//Getters and Setters

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



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
    
}