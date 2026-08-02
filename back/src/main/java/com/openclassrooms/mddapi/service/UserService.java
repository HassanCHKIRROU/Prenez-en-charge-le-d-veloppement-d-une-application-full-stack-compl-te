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

@Service
public class UserService {
	
	

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;
    
    
    
    
    
    

    public UserService(UserRepository userRepository,
                       SubscriptionRepository subscriptionRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    
    
    
    
    
    
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