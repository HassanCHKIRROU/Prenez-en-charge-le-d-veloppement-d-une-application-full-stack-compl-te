package com.openclassrooms.mddapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.request.ArticleRequest;
import com.openclassrooms.mddapi.dto.response.ArticleResponse;
import com.openclassrooms.mddapi.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ArticleRequest articleRequest;
    private ArticleResponse articleResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();

        articleRequest = new ArticleRequest();
        articleRequest.setTopicName("Java");
        articleRequest.setTitle("Test Article");
        articleRequest.setContent("Test Content");

        articleResponse = new ArticleResponse();
        articleResponse.setId(1L);
        articleResponse.setTitle("Test Article");
        articleResponse.setContent("Test Content");
        articleResponse.setCreatedAt(LocalDateTime.now());
        articleResponse.setUpdatedAt(LocalDateTime.now());
        articleResponse.setComments(new ArrayList<>());
    }

    @Test
    void shouldCreateArticle() throws Exception {
        when(articleService.createArticle(any(ArticleRequest.class))).thenReturn(articleResponse);

        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Article"));
    }

    @Test
    void shouldGetFeed() throws Exception {
        when(articleService.getFeed("desc")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/articles/feed")
                .param("sort", "desc"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetArticleById() throws Exception {
        when(articleService.getArticleById(1L)).thenReturn(articleResponse);

        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Article"));
    }
}