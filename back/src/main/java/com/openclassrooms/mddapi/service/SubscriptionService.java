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

/**
 * Service responsable de la gestion des abonnements des utilisateurs aux différents thèmes de l'application.
 * Cette classe contient la logique métier permettant à un utilisateur
 * authentifié de s'abonner ou de se désabonner d'un thème.
 *L'utilisateur actuellement connecté est identifié à partir du contexte de sécurité fourni par Spring Security.
 */

@Service
public class SubscriptionService {

	
	
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    
    
    
    
     //Constructeur
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository,
			TopicRepository topicRepository) {
		this.subscriptionRepository = subscriptionRepository;
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
	}
    
    
    
    
    
    
    /**
     * Abonne l'utilisateur actuellement authentifié à un thème.
     * La méthode récupère l'utilisateur connecté ainsi que le thèmecorrespondant à l'identifiant fourni.
     *  Elle vérifie ensuite quel'utilisateur n'est pas déjà abonné à ce thème avant de créer
     * et d'enregistrer le nouvel abonnement.
     *
     * @param topicId identifiant du thème auquel l'utilisateur souhaite s'abonner
     * @throws RuntimeException si l'utilisateur n'est pas trouvé,
     *                          si le thème n'existe pas ou si l'utilisateur
     *                          est déjà abonné au thème
     */
    
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

	
	
	
	
	
	
	
	
	
	/**
     * Désabonne l'utilisateur actuellement authentifié d'un thème.
     *La méthode vérifie que l'utilisateur est actuellement abonnéau thème avant de supprimer son abonnement.
     * @param topicId identifiant du thème dont l'utilisateur souhaite se désabonner
     * @throws RuntimeException si l'utilisateur n'est pas trouvé ou s'il n'est pas abonné au thème indiqué
     */
	
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