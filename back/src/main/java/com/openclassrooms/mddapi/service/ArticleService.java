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

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final SubscriptionRepository subscriptionRepository;

    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository,
                          UserRepository userRepository, TopicRepository topicRepository,
                          SubscriptionRepository subscriptionRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    
    
    
    
    
    
    
    /*
    public List<ArticleSummaryDTO> getFeed(String sort) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Article> articles = articleRepository.findAll();

        if ("asc".equalsIgnoreCase(sort)) {
            articles.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
        } else {
            articles.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
        }

        List<ArticleSummaryDTO> result = new ArrayList<>();
        for (Article article : articles) {
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
*/
    
   /* 
    public List<ArticleSummaryDTO> getFeed(String sort) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // ✅ Récupérer les articles des thèmes auxquels l'utilisateur est abonné
        List<Article> subscribedArticles = articleRepository.findArticlesBySubscribedTopics(user.getId());

        // ✅ Récupérer les articles créés par l'utilisateur (même s'il n'est pas abonné au thème)
        List<Article> userArticles = articleRepository.findByAuthorId(user.getId());

        // ✅ Fusionner les deux listes sans doublons
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

        // ✅ Trier selon le paramètre
        if ("asc".equalsIgnoreCase(sort)) {
            allArticles.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
        } else {
            allArticles.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
        }

        // ✅ Convertir en DTO
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
    
    */
    /*
   
    public List<ArticleSummaryDTO> getFeed(String sort) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // ✅ 1. Récupérer les articles des thèmes auxquels l'utilisateur est abonné
        List<Article> subscribedArticles = articleRepository.findArticlesBySubscribedTopics(user.getId());

        // ✅ 2. Récupérer les articles créés par l'utilisateur (même s'il n'est pas abonné)
        List<Article> userArticles = articleRepository.findByAuthorId(user.getId());

        // ✅ 3. Fusionner les deux listes sans doublons
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

        // ✅ 4. Trier
        if ("asc".equalsIgnoreCase(sort)) {
            allArticles.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
        } else {
            allArticles.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
        }

        // ✅ 5. Convertir en DTO
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
   
    */
    
    
    
    public List<ArticleSummaryDTO> getFeed(String sort) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // ✅ Récupérer les articles des thèmes auxquels l'utilisateur est abonné
        List<Article> subscribedArticles = articleRepository.findArticlesBySubscribedTopics(user.getId());

        // ✅ Récupérer les articles créés par l'utilisateur
        List<Article> userArticles = articleRepository.findByAuthorId(user.getId());

        // ✅ Fusionner les deux listes sans doublons
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

        // ✅ SI AUCUN ARTICLE TROUVÉ → afficher tous les articles
        /*
        if (allArticles.isEmpty()) {
            allArticles = articleRepository.findAll();
        }
*/
        // ✅ Trier
        if ("asc".equalsIgnoreCase(sort)) {
            allArticles.sort((a1, a2) -> a1.getCreatedAt().compareTo(a2.getCreatedAt()));
        } else {
            allArticles.sort((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt()));
        }

        // ✅ Convertir en DTO
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
    
    
    
    
    
    @Transactional
    public ArticleResponse createArticle(ArticleRequest request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User author = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // ✅ Vérifier si le thème existe déjà
            Topic topic = topicRepository.findByTitle(request.getTopicName())
                    .orElseGet(() -> {
                        // ✅ Créer un nouveau thème s'il n'existe pas
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
    
    /*
    @Transactional
    public ArticleResponse createArticle(ArticleRequest request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User author = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // ✅ Vérifier si le thème existe déjà
            Topic topic = topicRepository.findByTitle(request.getTopicName())
                    .orElseGet(() -> {
                        // ✅ Créer un nouveau thème s'il n'existe pas
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
    */
    
    /*
    
    @Transactional
    public ArticleResponse createArticle(ArticleRequest request) {
    	try {
    	System.out.println("début de création");
    	
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        System.out.println("Username "+username);
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        System.out.println("Author " +author.getUsername());

        Topic topic = new Topic();
        topic.setTitle(request.getTopicName());
        topic.setDescription("Thème créé automatiquement");
        topic = topicRepository.save(topic);
        
        System.out.println("Topic ID "+ topic.getId());

        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setAuthor(author);
        article.setTopic(topic);
        article = articleRepository.save(article);
        
        System.out.println("Article ID "+article.getId());

        return mapToResponse(article);
    	} catch (Exception e) {
            e.printStackTrace();  // ✅ Affiche l'erreur complète dans la console
            throw e;
        }
    }

  */  
    
    	
    public ArticleResponse getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        return mapToResponse(article);
        
        
    }

    /*
    @Transactional
    public ArticleResponse createArticle(ArticleRequest request) {
        try {
            System.out.println("🔵 ===== DEBUT createArticle =====");
            System.out.println("🔵 1 - topicName: " + request.getTopicName());
            System.out.println("🔵 2 - title: " + request.getTitle());
            System.out.println("🔵 3 - content: " + request.getContent());

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("🔵 4 - Username from SecurityContext: " + username);

            User author = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            System.out.println("🔵 5 - Author trouvé: " + author.getUsername() + " (ID: " + author.getId() + ")");

            Topic topic = new Topic();
            topic.setTitle(request.getTopicName());
            topic.setDescription("Thème créé automatiquement");
            System.out.println("🔵 6 - Topic créé en mémoire: " + topic.getTitle());

            topic = topicRepository.save(topic);
            System.out.println("🔵 7 - Topic sauvegardé en base avec ID: " + topic.getId());

            Article article = new Article();
            article.setTitle(request.getTitle());
            article.setContent(request.getContent());
            article.setAuthor(author);
            article.setTopic(topic);

            System.out.println("🔵 8 - Article créé en mémoire:");
            System.out.println("    - Title: " + article.getTitle());
            System.out.println("    - Content: " + article.getContent());
            System.out.println("    - Author: " + (article.getAuthor() != null ? article.getAuthor().getUsername() : "NULL"));
            System.out.println("    - Topic: " + (article.getTopic() != null ? article.getTopic().getTitle() : "NULL"));

            article = articleRepository.save(article);
            System.out.println("🔵 9 - Article sauvegardé en base avec ID: " + article.getId());

            ArticleResponse response = mapToResponse(article);
            System.out.println("🔵 10 - mapToResponse terminé avec succès");
            System.out.println("🔵 ===== FIN createArticle SUCCES =====");

            return response;

        } catch (Exception e) {
            System.err.println("🔴 ===== ERREUR dans createArticle =====");
            e.printStackTrace();
            throw new RuntimeException("Erreur création article: " + e.getMessage(), e);
        }
    }
    
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

    
    
    
    
    
    
    
    private ArticleResponse mapToResponse(Article article) {
    	
    	 System.out.println("🔵 mapToResponse - DEBUT");
    	    System.out.println("🔵 mapToResponse - Article ID: " + article.getId());
    	    System.out.println("🔵 mapToResponse - Author: " + (article.getAuthor() != null ? article.getAuthor().getUsername() : "NULL"));
    	    System.out.println("🔵 mapToResponse - Topic: " + (article.getTopic() != null ? article.getTopic().getTitle() : "NULL"));
    	    System.out.println("🔵 mapToResponse - Title: " + article.getTitle());
    	
    	
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