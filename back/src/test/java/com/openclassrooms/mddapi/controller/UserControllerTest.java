package com.openclassrooms.mddapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.UserProfileResponse;
import com.openclassrooms.mddapi.service.UserService;
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
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserProfileResponse profileResponse;
    private UpdateProfileRequest updateRequest;

    
    
    
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        profileResponse = new UserProfileResponse();
        profileResponse.setId(1L);
        profileResponse.setUsername("testuser");
        profileResponse.setEmail("test@example.com");
        profileResponse.setCreatedAt(LocalDateTime.now());
        profileResponse.setUpdatedAt(LocalDateTime.now());
        profileResponse.setSubscriptions(new ArrayList<>());

        updateRequest = new UpdateProfileRequest();
        updateRequest.setUsername("newusername");
        updateRequest.setEmail("new@example.com");
        updateRequest.setPassword("NewTest1234!");
    }

    
    
    
    @Test
    void shouldGetProfile() throws Exception {
        when(userService.getCurrentUserProfile()).thenReturn(profileResponse);

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    
    
    
    
    
    @Test
    void shouldUpdateProfile() throws Exception {
        when(userService.updateProfile(any(UpdateProfileRequest.class))).thenReturn(profileResponse);

        mockMvc.perform(put("/user/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}