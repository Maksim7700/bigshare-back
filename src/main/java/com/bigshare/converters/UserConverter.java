package com.bigshare.converters;

import com.bigshare.dtos.UserDTO;
import com.bigshare.model.user.User;

public class UserConverter {

    public static UserDTO convertToUserResponse(User user) {
        return new UserDTO(user.getUsername());
    }
}
