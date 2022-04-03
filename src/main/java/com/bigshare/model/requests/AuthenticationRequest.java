package com.bigshare.model.requests;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class AuthenticationRequest {

    @Length(message = "Min length 3 and max 26 symbols.", min = 3, max = 26)
    private String username;

    @Length(message = "Min length 5 symbols.", min = 5)
    private String password;
}
