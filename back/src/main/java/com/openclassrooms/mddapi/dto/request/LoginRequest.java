package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	
    @NotBlank(message = "Email ou nom d'utilisateur est requis")
    private String usernameOrEmail;
    

    @NotBlank(message = "Mot de passe est requis")
    private String password;
}