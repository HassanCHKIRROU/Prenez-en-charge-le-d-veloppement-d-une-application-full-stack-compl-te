package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Fournit les fonctionnalités nécessaires à la gestion des tokens JWT
 * utilisés pour l'authentification et la sécurisation de l'application.
 *permet notamment de :

 * générer un token JWT à partir des informations d'authentification 
 * extraire le nom d'utilisateur contenu dans un token 
 * valider l'intégrité et la validité d'un token JWT
 */

@Component
public class JwtTokenProvider {
	
	

    @Value("${jwt.secret}")
    private String jwtSecret;

    
    @Value("${jwt.expiration}")
    private int jwtExpiration;

    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

 
    
   
    
    /**
     * Génère un token JWT à partir des informations d'authentification
     * de l'utilisateur connecté.
     *
     * Le nom d'utilisateur est enregistré dans le champ {@code subject}
     *  du token. Le token contient également sa date de création et sa
     * date d'expiration.
     *
     * @param authentication objet contenant les informations d'authentification de l'utilisateur
     * @return un token JWT signé contenant l'identifiant de l'utilisateur
     */
    
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    
    
    
    
    
    
    /**
     * Extrait le nom d'utilisateur contenu dans un token JWT.
     *
     *Le token est d'abord analysé et sa signature est vérifiée à l'aide de la clé secrète de l'application.
     * Le nom d'utilisateur est ensuite récupéré depuis le champ {@code subject} du token.
     *
     * @param token token JWT dont le nom d'utilisateur doit être extrait
     * @return le nom d'utilisateur contenu dans le token
     * @throws io.jsonwebtoken.JwtException si le token est invalide, mal formé ou si sa signature
     *  ne peut pas être vérifiée
     */
    
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    
    
    
    
    
    
    
    /**
     * Vérifie la validité d'un token JWT.
     *La méthode vérifie notamment que le token est correctement signé,
     * qu'il peut être analysé et qu'il respecte les informations de
     * validité définies lors de sa génération, notamment sa date
     * d'expiration.
     *
     * @param token token JWT à vérifier
     * @return {@code true} si le token est valide, sinon {@code false}
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}