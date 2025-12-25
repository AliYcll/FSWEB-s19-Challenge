package com.workintech.twitter.dto;

import lombok.Data;

@Data
public class LikeRequest {
    private Long userId;
    private Long tweetId;
}
