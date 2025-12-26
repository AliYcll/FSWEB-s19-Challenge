package com.workintech.twitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.twitter.dto.requests.LikeRequest;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.GlobalExceptionHandler;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.security.JwtAuthenticationFilter;
import com.workintech.twitter.service.LikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LikeService likeService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void like_returnsOkWhenRequestValid() throws Exception {
        LikeRequest request = new LikeRequest();
        request.setUserId(1L);
        request.setTweetId(2L);

        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setId(2L);
        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);

        when(likeService.likeTweet(1L, 2L)).thenReturn(like);

        mockMvc.perform(post("/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void like_returnsBadRequestWhenUserIdMissing() throws Exception {
        String body = "{\"tweetId\":2}";

        mockMvc.perform(post("/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void dislike_returnsNotFoundWhenServiceThrows() throws Exception {
        LikeRequest request = new LikeRequest();
        request.setUserId(1L);
        request.setTweetId(2L);

        doThrow(new NotFoundException("Like not found."))
                .when(likeService).dislikeTweet(1L, 2L);

        mockMvc.perform(post("/dislike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
