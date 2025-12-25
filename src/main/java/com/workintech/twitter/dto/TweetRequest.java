package com.workintech.twitter.dto;

import lombok.Data;

@Data
public class TweetRequest {
    private Long userId;
    private String content;
}
