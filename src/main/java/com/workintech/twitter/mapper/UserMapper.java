package com.workintech.twitter.mapper;

import com.workintech.twitter.dto.responses.UserSummaryResponse;
import com.workintech.twitter.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserSummaryResponse toSummary(User user) {
        if (user == null) {
            return null;
        }
        UserSummaryResponse response = new UserSummaryResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setDisplayName(user.getDisplayName());
        return response;
    }
}
