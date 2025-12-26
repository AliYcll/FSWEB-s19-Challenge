package com.workintech.twitter.dto.responses;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Long tweetId;
    private UserSummaryResponse user;
}
