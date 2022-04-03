package com.crowdmarketing.exceptions.field;

import lombok.Data;

@Data
public class CustomFieldError {
    private String field;
    private String message;
}