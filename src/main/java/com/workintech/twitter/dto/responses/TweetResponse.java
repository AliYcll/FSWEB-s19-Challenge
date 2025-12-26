package com.workintech.twitter.dto.responses;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class TweetResponse {
    private Long id;
    private String content;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private UserSummaryResponse user;
}
