package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

	
	
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    
    
    
    

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository,
			TopicRepository topicRepository) {
		this.subscriptionRepository = subscriptionRepository;
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
	}
    
    
    
    
    
    
	@Transactional
    public void subscribe(Long topicId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Thème non trouvé"));

        // Vérifier si déjà abonné
        if (subscriptionRepository.existsByUserIdAndTopicId(user.getId(), topicId)) {
            throw new RuntimeException("Vous êtes déjà abonné à ce thème");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
                
        subscriptionRepository.save(subscription);
    }

	
	
	
	
	
	
	
	
    @Transactional
    public void unsubscribe(Long topicId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier si abonné
        if (!subscriptionRepository.existsByUserIdAndTopicId(user.getId(), topicId)) {
            throw new RuntimeException("Vous n'êtes pas abonné à ce thème");
        }

        subscriptionRepository.deleteByUserIdAndTopicId(user.getId(), topicId);
    }
}