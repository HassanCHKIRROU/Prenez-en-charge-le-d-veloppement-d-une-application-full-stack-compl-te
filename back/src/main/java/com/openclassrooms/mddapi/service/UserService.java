package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.UserProfileResponse;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsable de la gestion des utilisateurs.
 * Cette classe contient la logique métier permettant de consulteret de modifier le profil de l'utilisateur
 *  actuellement authentifié.
 * Elle permet  de récupérer les informations personnelles de l'utilisateur et ses abonnements aux différents thèmes,
 * et de modifier son nom d'utilisateur, son adresse e-mail et son mot de passe.</p>
 */

@Service
public class UserService {
	
	

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;
    
    
    
    
    
    
     //Constructeur
    public UserService(UserRepository userRepository,
                       SubscriptionRepository subscriptionRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    
    
    
    
    
    
    
    /**
     * Récupère le profil de l'utilisateur actuellement authentifié.
     * La méthode identifie l'utilisateur connecté à partir du contextede sécurité Spring Security,
     *  puis récupère ses informations et la liste des thèmes auxquels il est abonné.
     * Les abonnements sont convertis en objets {TopicDTO}
     * afin d'être inclus dans la réponse retournée par le service.</p>
     *
     * @return le profil de l'utilisateur authentifié sous forme de { UserProfileResponse}
     * @throws RuntimeException si l'utilisateur actuellement authentifié n'est pas trouvé
     */
    
    public UserProfileResponse getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());

        List<TopicDTO> topicDTOs = new ArrayList<>();
        for (Subscription sub : subscriptions) {
            TopicDTO dto = new TopicDTO();
            dto.setId(sub.getTopic().getId());
            dto.setTitle(sub.getTopic().getTitle());
            dto.setDescription(sub.getTopic().getDescription());
            dto.setSubscribed(true);
            topicDTOs.add(dto);
        }

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setSubscriptions(topicDTOs);

        return response;
    }

    
    
    
    
    
    
    
    
    /**
     * Met à jour le profil de l'utilisateur actuellement authentifié.
     * La méthode permet de modifier le nom d'utilisateur et l'adresse email.
     *  Elle vérifie que les nouvelles valeurs ne sont pas déjà utilisées par un autre utilisateur.
     * Le mot de passe est modifié uniquement lorsqu'une nouvelle valeur est fournie. Dans ce cas,
     *  le nouveau mot de passe est encodé avant son enregistrement en base de données.
     * Après la mise à jour, le profil complet de l'utilisateur, incluant ses abonnements, est retourné.</p>
     *
     * @param request données contenant les nouvelles informations du profil utilisateur
     * @return le profil utilisateur mis à jour sous forme de { UserProfileResponse}
     * @throws RuntimeException si l'utilisateur n'est pas trouvé,
     *                          si le nom d'utilisateur est déjà utilisé
     *                          ou si l'adresse e-mail est déjà utilisée
     */
    
    @Transactional
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!user.getUsername().equals(request.getUsername()) &&
                userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà utilisé");
        }

        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);

        List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());
        List<TopicDTO> topicDTOs = new ArrayList<>();
        for (Subscription sub : subscriptions) {
            TopicDTO dto = new TopicDTO();
            dto.setId(sub.getTopic().getId());
            dto.setTitle(sub.getTopic().getTitle());
            dto.setDescription(sub.getTopic().getDescription());
            dto.setSubscribed(true);
            topicDTOs.add(dto);
        }

        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setSubscriptions(topicDTOs);

        return response;
    }
}