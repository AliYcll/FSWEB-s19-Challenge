package com.workintech.twitter.dto.responses;

import lombok.Data;

@Data
public class RetweetResponse {
    private Long id;
    private Long userId;
    private Long tweetId;
}
