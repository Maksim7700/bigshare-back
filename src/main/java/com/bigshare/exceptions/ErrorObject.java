package com.bigshare.exceptions;

import lombok.Data;

@Data
public class ErrorObject {

    private int status;
    private ErrorCode errorCode;
    private String message;

    public ErrorObject() {
    }

    public ErrorObject(int status, ErrorCode errorCode, String message) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }
}
