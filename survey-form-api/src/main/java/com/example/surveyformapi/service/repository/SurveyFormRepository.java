package com.example.surveyformapi.service.repository;


import com.example.surveyformapi.service.model.SurveyForm;
import org.springframework.data.repository.CrudRepository;

public interface SurveyFormRepository extends CrudRepository<SurveyForm, String> {
}
