package com.workintech.twitter.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RetweetRequest {
    @NotNull(message = "User id bos olamaz.")
    private Long userId;

    @NotNull(message = "Tweet id bos olamaz.")
    private Long tweetId;
}
