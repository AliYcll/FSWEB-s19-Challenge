package com.workintech.twitter.mapper;

import com.workintech.twitter.dto.responses.CommentResponse;
import com.workintech.twitter.entity.Comment;

public class CommentMapper {

    private CommentMapper() {
    }

    public static CommentResponse toResponse(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(comment.getUpdatedAt());
        response.setTweetId(comment.getTweet() != null ? comment.getTweet().getId() : null);
        response.setUser(UserMapper.toSummary(comment.getUser()));
        return response;
    }
}
