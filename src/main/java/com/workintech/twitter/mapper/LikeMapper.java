package com.workintech.twitter.mapper;

import com.workintech.twitter.dto.responses.LikeResponse;
import com.workintech.twitter.entity.Like;

public class LikeMapper {

    private LikeMapper() {
    }

    public static LikeResponse toResponse(Like like) {
        if (like == null) {
            return null;
        }
        LikeResponse response = new LikeResponse();
        response.setId(like.getId());
        response.setUserId(like.getUser() != null ? like.getUser().getId() : null);
        response.setTweetId(like.getTweet() != null ? like.getTweet().getId() : null);
        return response;
    }
}
