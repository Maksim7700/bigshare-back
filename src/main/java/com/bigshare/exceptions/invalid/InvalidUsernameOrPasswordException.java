package com.bigshare.exceptions.invalid;

public class InvalidUsernameOrPasswordException extends RuntimeException{

    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}