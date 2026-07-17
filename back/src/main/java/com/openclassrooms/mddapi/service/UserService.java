package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.UserProfileResponse;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

	
	
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;

    
    
    
    public UserProfileResponse getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());

        List<TopicDTO> topicDTOs = subscriptions.stream()
                .map(sub -> TopicDTO.builder()
                        .id(sub.getTopic().getId())
                        .title(sub.getTopic().getTitle())
                        .description(sub.getTopic().getDescription())
                        .subscribed(true)
                        .build())
                .collect(Collectors.toList());

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .subscriptions(topicDTOs)
                .build();
    }

    
    
    
    
    
    
    
    
    
// Mis à jour profil 
    @Transactional
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si le nouveau nom d'utilisateur est déjà pris (si changé)
        if (!user.getUsername().equals(request.getUsername()) &&
                userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà utilisé");
        }

        // Vérifier si le nouvel email est déjà pris (si changé)
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Mise à jour des champs
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Si un nouveau mot de passe est fourni, le encoder et le mettre à jour
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);

     /*   //  Mettre à jour le SecurityContext avec le nouveau nom d'utilisateur
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    authentication.getCredentials(),
                    authentication.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
*/
        // Récupérer les abonnements pour la réponse
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());
        List<TopicDTO> topicDTOs = subscriptions.stream()
                .map(sub -> TopicDTO.builder()
                        .id(sub.getTopic().getId())
                        .title(sub.getTopic().getTitle())
                        .description(sub.getTopic().getDescription())
                        .subscribed(true)
                        .build())
                .collect(Collectors.toList());

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .subscriptions(topicDTOs)
                .build();
    }
}