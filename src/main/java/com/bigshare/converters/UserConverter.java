package com.bigshare.converters;

import com.bigshare.model.responses.UserResponse;
import com.bigshare.model.user.entity.User;

public class UserConverter {

    public static UserResponse convertToUserResponse(User user) {
        return new UserResponse(user.getUsername());
    }
}
