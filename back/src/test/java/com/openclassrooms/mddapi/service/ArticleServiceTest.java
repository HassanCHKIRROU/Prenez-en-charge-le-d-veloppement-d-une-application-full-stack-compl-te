package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleSummaryDTO;
import com.openclassrooms.mddapi.dto.request.ArticleRequest;
import com.openclassrooms.mddapi.dto.request.CommentRequest;
import com.openclassrooms.mddapi.dto.response.ArticleResponse;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)  // Permet d'éviter les erreurs de stubbings inutiles
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ArticleService articleService;

    
    private User author;
    private Topic topic;
    private Article article;
    private ArticleRequest articleRequest;
    private CommentRequest commentRequest;

    
    
    
    @BeforeEach
    void setUp() {
        // Configuration du contexte de sécurité
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");

        author = new User();
        author.setId(1L);
        author.setUsername("testuser");
        author.setEmail("test@example.com");

        topic = new Topic();
        topic.setId(1L);
        topic.setTitle("Java");
        topic.setDescription("Java programming");

        article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");
        article.setContent("Test Content");
        article.setAuthor(author);
        article.setTopic(topic);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        articleRequest = new ArticleRequest();
        articleRequest.setTopicName("Java");
        articleRequest.setTitle("Test Article");
        articleRequest.setContent("Test Content");

        commentRequest = new CommentRequest();
        commentRequest.setContent("Test Comment");
    }

    
    
    
    
    @Test
    void shouldCreateArticleSuccessfully() {
        // Stubbings nécessaires pour ce test
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(author));
        when(topicRepository.findByTitle("Java")).thenReturn(Optional.of(topic));
        when(articleRepository.save(any(Article.class))).thenReturn(article);
        when(commentRepository.findByArticleIdOrderByCreatedAtAsc(anyLong())).thenReturn(new ArrayList<>());

        ArticleResponse response = articleService.createArticle(articleRequest);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Test Article");
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    
    
    
    
    
    @Test
    void shouldCreateTopicIfNotExists() {
        //Stubbings nécessaires pour ce test
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(author));
        when(topicRepository.findByTitle("NewTopic")).thenReturn(Optional.empty());
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);
        when(articleRepository.save(any(Article.class))).thenReturn(article);
        when(commentRepository.findByArticleIdOrderByCreatedAtAsc(anyLong())).thenReturn(new ArrayList<>());

        articleRequest.setTopicName("NewTopic");

        ArticleResponse response = articleService.createArticle(articleRequest);

        assertThat(response).isNotNull();
        verify(topicRepository, times(1)).save(any(Topic.class));
    }

    
    
    
    
    
    
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        //  Stubbings nécessaires pour ce test
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.createArticle(articleRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Erreur création article: Utilisateur non trouvé");
        verify(articleRepository, never()).save(any(Article.class));
    }

    
    
    
    
    
    
    @Test
    void shouldGetFeedSuccessfully() {
        // Stubbings nécessaires pour ce test
        List<Article> articles = List.of(article);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(author));
        when(articleRepository.findArticlesBySubscribedTopics(1L)).thenReturn(articles);
        when(articleRepository.findByAuthorId(1L)).thenReturn(new ArrayList<>());

        List<ArticleSummaryDTO> feed = articleService.getFeed("desc");

        assertThat(feed).isNotEmpty();
        assertThat(feed.size()).isEqualTo(1);
        assertThat(feed.get(0).getTitle()).isEqualTo("Test Article");
    }

    
    
    
    
    
    
    
    @Test
    void shouldReturnEmptyFeedWhenNoArticles() {
        // Stubbings nécessaires pour ce test
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(author));
        when(articleRepository.findArticlesBySubscribedTopics(1L)).thenReturn(new ArrayList<>());
        when(articleRepository.findByAuthorId(1L)).thenReturn(new ArrayList<>());

        List<ArticleSummaryDTO> feed = articleService.getFeed("desc");

        assertThat(feed).isEmpty();
    }

    
    
    
    
    
    @Test
    void shouldGetArticleByIdSuccessfully() {
        //  Stubbings nécessaires pour ce test
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(commentRepository.findByArticleIdOrderByCreatedAtAsc(1L)).thenReturn(new ArrayList<>());

        ArticleResponse response = articleService.getArticleById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    
    
    
    
    
    @Test
    void shouldThrowExceptionWhenArticleNotFound() {
        // Stubbings nécessaires pour ce test
        when(articleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.getArticleById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Article non trouvé");
    }
}