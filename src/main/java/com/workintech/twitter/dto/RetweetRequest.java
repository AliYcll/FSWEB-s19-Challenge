package com.workintech.twitter.dto;

import lombok.Data;

@Data
public class RetweetRequest {
    private Long userId;
    private Long tweetId;
}
