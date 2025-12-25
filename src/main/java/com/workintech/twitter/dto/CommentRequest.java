package com.workintech.twitter.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long userId;
    private Long tweetId;
    private String content;
}
