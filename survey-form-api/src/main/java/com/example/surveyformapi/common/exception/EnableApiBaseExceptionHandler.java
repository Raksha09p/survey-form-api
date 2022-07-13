package com.example.surveyformapi.common.exception;

import java.lang.annotation.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Import(SurveyFormApiBaseExceptionHandler.class)
@Configuration
public @interface EnableApiBaseExceptionHandler {

}