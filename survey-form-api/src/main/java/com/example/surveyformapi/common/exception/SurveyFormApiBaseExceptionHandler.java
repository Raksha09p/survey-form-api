package com.example.surveyformapi.common.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
public class SurveyFormApiBaseExceptionHandler extends ResponseEntityExceptionHandler{
    private static final String unReadableRequest    = "Not able to read incoming request";
    private static final String methodNotSupported   = " method is not supported for this request. Supported methods are ";
    private static final String internalErrorOccurred = "Error occurred";
    private static final String missedParam          = "Missing required request parameter";

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(methodNotSupported);
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        SurveyFormApiError surveyFormApiError = new SurveyFormApiError(status,
                ex.getLocalizedMessage(), builder.toString());

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(surveyFormApiError, new HttpHeaders(), status);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        SurveyFormApiError surveyFormApiError = new SurveyFormApiError(
                HttpStatus.INTERNAL_SERVER_ERROR, internalErrorOccurred, ex.getLocalizedMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(surveyFormApiError, new HttpHeaders(), surveyFormApiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        SurveyFormApiError surveyFormApiError = new SurveyFormApiError(status,
                unReadableRequest, ex.getMostSpecificCause().getLocalizedMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(surveyFormApiError, new HttpHeaders(), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        SurveyFormApiError surveyFormApiError = new SurveyFormApiError(status, unReadableRequest,
                errors);
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(surveyFormApiError, new HttpHeaders(), status);
    }


    @ExceptionHandler({ HttpMessageConversionException.class })
    public ResponseEntity<Object> handleHttpMessageConversionException(HttpMessageConversionException ex, WebRequest request) {
        SurveyFormApiError surveyFormApiError = new SurveyFormApiError(HttpStatus.BAD_REQUEST, unReadableRequest,
                ex.getMostSpecificCause().getLocalizedMessage());
        return new ResponseEntity<>(surveyFormApiError, new HttpHeaders(), surveyFormApiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        SurveyFormApiError surveyFormApiError = new SurveyFormApiError(HttpStatus.BAD_REQUEST, missedParam, ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(surveyFormApiError, new HttpHeaders(), surveyFormApiError.getStatus());
    }

    @ExceptionHandler({ HttpStatusCodeException.class })
    public ResponseEntity<Object> handleHttpStatusCodeException(HttpStatusCodeException ex) {
        SurveyFormApiError surveyFormApiError = new SurveyFormApiError(ex.getStatusCode(), internalErrorOccurred,
                ex.getMostSpecificCause().getLocalizedMessage());
        return new ResponseEntity<>(surveyFormApiError, new HttpHeaders(), surveyFormApiError.getStatus());
    }

    private String generateMessage(String exceptionMessage, List<String> errors) {
        return exceptionMessage + "\n" + errors.stream().map(error -> error + "\n").collect(Collectors.joining());
    }
}

