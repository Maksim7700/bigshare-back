package com.bigshare.exceptions.field;

import lombok.Data;

@Data
public class CustomFieldError {
    private String field;
    private String message;
}