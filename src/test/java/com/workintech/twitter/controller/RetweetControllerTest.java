package com.workintech.twitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.twitter.dto.requests.RetweetRequest;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.GlobalExceptionHandler;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.security.JwtAuthenticationFilter;
import com.workintech.twitter.service.RetweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RetweetController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class RetweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RetweetService retweetService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void retweet_returnsOkWhenValid() throws Exception {
        RetweetRequest request = new RetweetRequest();
        request.setUserId(1L);
        request.setTweetId(2L);

        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setId(2L);
        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);

        when(retweetService.retweet(1L, 2L)).thenReturn(retweet);

        mockMvc.perform(post("/retweet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void retweet_returnsBadRequestWhenUserIdMissing() throws Exception {
        String body = "{\"tweetId\":2}";

        mockMvc.perform(post("/retweet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteRetweet_returnsNotFoundWhenServiceThrows() throws Exception {
        doThrow(new NotFoundException("Retweet not found."))
                .when(retweetService).deleteRetweetById(10L, 1L);

        mockMvc.perform(delete("/retweet/10").param("userId", "1"))
                .andExpect(status().isNotFound());
    }
}
