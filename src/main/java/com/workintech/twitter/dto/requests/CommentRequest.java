package com.workintech.twitter.dto.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CommentRequest {
    @NotNull(message = "User id bos olamaz.")
    private Long userId;

    @NotNull(message = "Tweet id bos olamaz.")
    private Long tweetId;

    @NotBlank(message = "Icerik bos olamaz.")
    @Size(max = 255, message = "Icerik en fazla 255 karakter olmali.")
    private String content;
}
