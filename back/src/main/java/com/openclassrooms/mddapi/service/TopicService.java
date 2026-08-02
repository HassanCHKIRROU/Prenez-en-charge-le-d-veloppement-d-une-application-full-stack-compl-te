package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
	
	

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    
    
    
    
    

    public TopicService(TopicRepository topicRepository, UserRepository userRepository,
                        SubscriptionRepository subscriptionRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    
    
    
    
    
    
    
    public List<TopicDTO> getAllTopics() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Topic> topics = topicRepository.findAll();

        List<TopicDTO> result = new ArrayList<>();
        for (Topic topic : topics) {
            boolean isSubscribed = subscriptionRepository.existsByUserIdAndTopicId(user.getId(), topic.getId());
            TopicDTO dto = new TopicDTO();
            dto.setId(topic.getId());
            dto.setTitle(topic.getTitle());
            dto.setDescription(topic.getDescription());
            dto.setSubscribed(isSubscribed);
            result.add(dto);
        }
        return result;
    }
}