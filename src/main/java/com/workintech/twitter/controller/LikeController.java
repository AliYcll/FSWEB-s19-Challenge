package com.workintech.twitter.controller;

import com.workintech.twitter.dto.requests.LikeRequest;
import com.workintech.twitter.dto.responses.LikeResponse;
import com.workintech.twitter.mapper.LikeMapper;
import com.workintech.twitter.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public LikeResponse like(@RequestBody @Valid LikeRequest request) {
        return LikeMapper.toResponse(
                likeService.likeTweet(request.getUserId(), request.getTweetId())
        );
    }

    @PostMapping("/dislike")
    public void dislike(@RequestBody @Valid LikeRequest request) {
        likeService.dislikeTweet(request.getUserId(), request.getTweetId());
    }

}
