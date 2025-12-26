package com.workintech.twitter.dto.responses;

import lombok.Data;

@Data
public class UserSummaryResponse {
    private Long id;
    private String username;
    private String displayName;
}
