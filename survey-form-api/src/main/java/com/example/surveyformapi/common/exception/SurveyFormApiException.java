package com.example.surveyformapi.common.exception;
import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class SurveyFormApiException extends NestedRuntimeException {

    private static final long serialVersionUID = -3161358505573350986L;

    private final List<String> errors;
    private final HttpStatus   httpStatus;

    public SurveyFormApiException(String exceptionMessage, List<String> errors, HttpStatus httpStatus) {
        super(exceptionMessage);
        this.errors = errors;
        this.httpStatus = httpStatus;
    }
}