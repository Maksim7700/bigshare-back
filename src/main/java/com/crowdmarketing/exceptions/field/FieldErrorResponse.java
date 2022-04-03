package com.crowdmarketing.exceptions.field;

import lombok.Data;

import java.util.List;

@Data
public class FieldErrorResponse {
    private List<CustomFieldError> fieldErrors;
}
