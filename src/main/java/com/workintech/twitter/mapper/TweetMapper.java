package com.workintech.twitter.mapper;

import com.workintech.twitter.dto.responses.TweetResponse;
import com.workintech.twitter.entity.Tweet;

public class TweetMapper {

    private TweetMapper() {
    }

    public static TweetResponse toResponse(Tweet tweet) {
        if (tweet == null) {
            return null;
        }
        TweetResponse response = new TweetResponse();
        response.setId(tweet.getId());
        response.setContent(tweet.getContent());
        response.setCreatedAt(tweet.getCreatedAt());
        response.setUpdatedAt(tweet.getUpdatedAt());
        response.setUser(UserMapper.toSummary(tweet.getUser()));
        return response;
    }
}
