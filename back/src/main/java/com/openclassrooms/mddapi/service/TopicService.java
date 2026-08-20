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

/**
 * Service responsable de la gestion des thèmes de l'application.
 * Cette classe permet de récupérer la liste des thèmes disponibles et de déterminer, pour chaque thème,
 *  si l'utilisateur actuellement authentifié y est abonné.</p>
 * Les données récupérées depuis la base de données sont converties
 * en objets {TopicDTO} destinés à être utilisés par les couches supérieures de l'application.
 */

@Service
public class TopicService {
	
	

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    
    
    
    
    
    // Constructeur
    public TopicService(TopicRepository topicRepository, UserRepository userRepository,
                        SubscriptionRepository subscriptionRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    
    
    
    
    
    
    
    /**
     * Récupère l'ensemble des thèmes disponibles dans l'application.
     * La méthode identifie tout d'abord l'utilisateur actuellement authentifié à partir du contexte de sécurité Spring Security.
     * Elle récupère ensuite tous les thèmes disponibles et détermine pour chacun d'eux si l'utilisateur est déjà abonné.
     * Chaque thème est converti en { TopicDTO}. Le champ
     * { subscribed} permet d'indiquer si l'utilisateur connecté est actuellement abonné au thème concerné.</p>
     *
     * @return liste des thèmes disponibles sous forme de { TopicDTO}, avec leur statut d'abonnement
     * @throws RuntimeException si l'utilisateur actuellement authentifié n'est pas trouvé
     */
    
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