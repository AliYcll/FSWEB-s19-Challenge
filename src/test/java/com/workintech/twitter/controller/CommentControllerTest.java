package com.workintech.twitter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.twitter.dto.requests.CommentRequest;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.GlobalExceptionHandler;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.security.JwtAuthenticationFilter;
import com.workintech.twitter.service.CommentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void createComment_returnsOkWhenValid() throws Exception {
        CommentRequest request = new CommentRequest();
        request.setUserId(1L);
        request.setTweetId(2L);
        request.setContent("hi");

        User user = new User();
        user.setId(1L);
        Tweet tweet = new Tweet();
        tweet.setId(2L);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setContent("hi");

        when(commentService.createComment(1L, 2L, "hi")).thenReturn(comment);

        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateComment_returnsBadRequestWhenContentMissing() throws Exception {
        String body = "{\"userId\":1,\"tweetId\":2}";

        mockMvc.perform(put("/comment/10")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteComment_returnsNotFoundWhenServiceThrows() throws Exception {
        doThrow(new NotFoundException("Comment not found."))
                .when(commentService).deleteComment(10L, 1L);

        mockMvc.perform(delete("/comment/10").param("userId", "1"))
                .andExpect(status().isNotFound());
    }
}
