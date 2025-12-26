package com.workintech.twitter.mapper;

import com.workintech.twitter.dto.responses.RetweetResponse;
import com.workintech.twitter.entity.Retweet;

public class RetweetMapper {

    private RetweetMapper() {
    }

    public static RetweetResponse toResponse(Retweet retweet) {
        if (retweet == null) {
            return null;
        }
        RetweetResponse response = new RetweetResponse();
        response.setId(retweet.getId());
        response.setUserId(retweet.getUser() != null ? retweet.getUser().getId() : null);
        response.setTweetId(retweet.getTweet() != null ? retweet.getTweet().getId() : null);
        return response;
    }
}
