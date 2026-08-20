package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleSummaryDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.request.ArticleRequest;
import com.openclassrooms.mddapi.dto.request.CommentRequest;
import com.openclassrooms.mddapi.dto.response.ArticleResponse;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service chargé de la gestion des articles et des commentaires.
 *Cette classe contient la logique métier permettant notamment de :
 * 
 *     récupérer le fil d'actualité de l'utilisateur 
 *     créer un nouvel article 
 *     récupérer un article par son identifiant 
 *     ajouter un commentaire à un article 
 *     convertir les entités métier en objets DTO destinés à l'API.

 *Le service utilise les repositories nécessaires pour accéderaux données persistées concernant les articles commentaires,
 * utilisateurs, thèmes et abonnements.
 */

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;
    
    
    
    
    
    //Constructeur
    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository,
                          UserRepository userRepository, TopicRepository topicRepository,
                          SubscriptionRepository subscriptionRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    
    
    
    
    
    
    /**
     * Récupère le fil d'actualité de l'utilisateur actuellement connecté.
     *Le fil d'actualité contient :
     * 
     *     les articles publiés dans les thèmes auxquels l'utilisateur est abonné 
     *     les articles créés par l'utilisateur lui-même.
     *Les articles en double sont supprimés puis la liste est triéeselon la date de création.
     * Le paramètre {@code sort} permet de choisir l'ordre croissant ou décroissant.
     *
     * Les articles sont ensuite convertis en objets {ArticleSummaryDTO}. Le contenu est limité à 100 caractères
     * dans le résumé.
     *
     * @param sort ordre de tri des articles. La valeur {@code "asc"} correspond à un tri croissant ; toute autre valeur
     *        entraîne un tri décroissant
     * @return liste des articles correspondant au fil d'actualité
     *         de l'utilisateur connecté
     * @throws RuntimeException si l'utilisateur connecté n'est pas trouvé
     */
    
    public List<ArticleSummaryDTO> getFeed(String sort) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        //  Récupérer les articles des thèmes auxquels l'utilisateur est abonné
        List<Article> subscribedArticles = articleRepository.findArticlesBySubscribedTopics(user.getId());

        //  Récupérer les articles créés par l'utilisateur
        List<Article> userArticles = articleRepository.findByAuthorId(user.getId());

        //  Fusionner les deux listes sans doublons
        Set<Long> articleIds = new HashSet<>();
        List<Article> allArticles = new ArrayList<>();

        for (Article article : subscribedArticles) {
            if (articleIds.add(article.getId())) {
                allArticles.add(article);
            }
        }

        for (Article article : userArticles) {
            if (articleIds.add(article.getId())) {
                allArticles.add(article);
            }
        }

       
        //  Trier
        if ("asc".equalsIgnoreCase(sort)) {
            allArticles.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
        } else {
            allArticles.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
        }

        // Convertir en DTO
        List<ArticleSummaryDTO> result = new ArrayList<>();
        for (Article article : allArticles) {
            ArticleSummaryDTO dto = new ArticleSummaryDTO();
            dto.setId(article.getId());
            dto.setTitle(article.getTitle());
            String content = article.getContent();
            if (content.length() > 100) {
                content = content.substring(0, 100) + "...";
            }
            dto.setContent(content);
            dto.setAuthorUsername(article.getAuthor().getUsername());
            dto.setTopicTitle(article.getTopic().getTitle());
            dto.setCreatedAt(article.getCreatedAt());
            result.add(dto);
        }
        return result;
    }
    
    
    
    
    
    
    
    
    /**
     * Crée un nouvel article à partir des données fournies.
     * L'utilisateur actuellement authentifié est utilisé comme auteur de l'article.
     *  Le thème indiqué dans la requête est recherché dans la base de données.
     *   S'il n'existe pas, un nouveau thème est créé automatiquement.
     *
     * <p>Une fois l'article enregistré, il est converti en {ArticleResponse} afin d'être retourné à l'appelant.
     *
     * @param request données nécessaires à la création de l'article
     * @return l'article créé sous forme de { ArticleResponse}
     * @throws RuntimeException si l'utilisateur n'est pas trouvé ou si  une erreur survient lors de la création
     */
    
    
    @Transactional
    public ArticleResponse createArticle(ArticleRequest request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User author = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            //  Vérifier si le thème existe déjà
            Topic topic = topicRepository.findByTitle(request.getTopicName())
                    .orElseGet(() -> {
                        //  Créer un nouveau thème s'il n'existe pas
                        Topic newTopic = new Topic();
                        newTopic.setTitle(request.getTopicName());
                        newTopic.setDescription("Thème créé automatiquement");
                        return topicRepository.save(newTopic);
                    });

            Article article = new Article();
            article.setTitle(request.getTitle());
            article.setContent(request.getContent());
            article.setAuthor(author);
            article.setTopic(topic);
            article = articleRepository.save(article);

            return mapToResponse(article);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur création article: " + e.getMessage(), e);
        }
    }
    
   
    
    
    
    
    /**
     * Recherche un article à partir de son identifiant.
     * Si l'article est trouvé, il est converti en{ArticleResponse}, incluant son auteur,
     * son thème et ses commentaires.
     *
     * @param articleId identifiant de l'article recherché
     * @return l'article sous forme de {ArticleResponse}
     * @throws RuntimeException si aucun article correspondant à l'identifiant fourni n'est trouvé
     */
    	
    public ArticleResponse getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        return mapToResponse(article);
        
        
    }

   
    
    
    
    
    
    /**
     * Ajoute un commentaire à un article.
     *L'utilisateur actuellement authentifié est associé comme auteur du commentaire.
     * Le commentaire est ensuite enregistré en basede données et converti en { CommentDTO}.
     *
     * @param articleId identifiant de l'article auquel le commentaire doit être associé
     * @param request données contenant le contenu du commentaire
     * @return le commentaire créé sous forme de {CommentDTO}
     * @throws RuntimeException si l'utilisateur ou l'article n'est pas trouvé
     */
    
    @Transactional
    public CommentDTO addComment(Long articleId, CommentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setArticle(article);
        comment.setAuthor(author);
        comment = commentRepository.save(comment);

        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthorUsername(author.getUsername());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    
    
    
    
    
    
    
    /**
     * Convertit une entité { Article} en objet {ArticleResponse}.
     * Cette méthode récupère également les commentaires associés à l'article et construit les DTO correspondants pour l'auteur,
     * le thème et les commentaires.
     *
     * La méthode est privée car elle est uniquement utilisée à l'intérieur de ce service pour préparer les réponses
     * retournées par les différentes opérations sur les articles.
     *
     * @param article entité article à convertir
     * @return l'article converti sous forme de { ArticleResponse}
     */
    
    private ArticleResponse mapToResponse(Article article) {
    	
        List<CommentDTO> commentDTOs = commentRepository.findByArticleIdOrderByCreatedAtAsc(article.getId())
                .stream()
                .map(comment -> {
                    CommentDTO dto = new CommentDTO();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setAuthorUsername(comment.getAuthor().getUsername());
                    dto.setCreatedAt(comment.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        UserDTO authorDTO = new UserDTO();
        authorDTO.setId(article.getAuthor().getId());
        authorDTO.setUsername(article.getAuthor().getUsername());
        authorDTO.setEmail(article.getAuthor().getEmail());

        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setId(article.getTopic().getId());
        topicDTO.setTitle(article.getTopic().getTitle());
        topicDTO.setDescription(article.getTopic().getDescription());

        ArticleResponse response = new ArticleResponse();
        response.setId(article.getId());
        response.setTitle(article.getTitle());
        response.setContent(article.getContent());
        response.setAuthor(authorDTO);
        response.setTopic(topicDTO);
        response.setCreatedAt(article.getCreatedAt());
        response.setUpdatedAt(article.getUpdatedAt());
        response.setComments(commentDTOs);

        return response;
    }
}