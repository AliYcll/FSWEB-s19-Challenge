package com.workintech.twitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.twitter.dto.requests.TweetRequest;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.GlobalExceptionHandler;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.security.JwtAuthenticationFilter;
import com.workintech.twitter.service.TweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TweetService tweetService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void createTweet_returnsOkWhenValid() throws Exception {
        TweetRequest request = new TweetRequest();
        request.setUserId(1L);
        request.setContent("hello");

        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent("hello");

        when(tweetService.createTweet(1L, "hello")).thenReturn(tweet);

        mockMvc.perform(post("/tweet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void createTweet_returnsBadRequestWhenContentMissing() throws Exception {
        String body = "{\"userId\":1}";

        mockMvc.perform(post("/tweet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById_returnsNotFoundWhenServiceThrows() throws Exception {
        when(tweetService.findById(99L)).thenThrow(new NotFoundException("Tweet not found."));

        mockMvc.perform(get("/tweet/findById").param("id", "99"))
                .andExpect(status().isNotFound());
    }
}
