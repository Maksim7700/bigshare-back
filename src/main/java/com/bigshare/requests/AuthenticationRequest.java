package com.bigshare.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Length(message = "Min length 3 and max 26 symbols.", min = 3, max = 26)
    private String username;

    @Length(message = "Min length 5 symbols.", min = 5)
    private String password;
}
