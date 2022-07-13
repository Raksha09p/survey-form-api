package com.example.surveyformapi.service.controller;

import com.example.surveyformapi.service.model.SurveyForm;
import com.example.surveyformapi.service.repository.SurveyFormRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin("http://localhost:3000")
@RestController
@Slf4j
@RequestMapping("/surveyForm")
public class SurveyFormController {

    private final SurveyFormRepository surveyFormRepository;
    HttpHeaders responseHeaders = new HttpHeaders();

    public SurveyFormController(SurveyFormRepository surveyFormRepository) {
        this.surveyFormRepository = surveyFormRepository;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<SurveyForm>> createSurveyForm(@Valid @RequestBody SurveyForm surveyForm) throws JsonProcessingException {
        log.info("Request received from engine" + surveyForm);
        String surveyName = surveyForm.getSurveyName();
        Optional<SurveyForm> isSurveyPresent = surveyFormRepository.findById(surveyName);
        if (isSurveyPresent.isPresent()) {
            responseHeaders.set("Message", "Element with the same name already exists");
            return new ResponseEntity(responseHeaders, HttpStatus.CONFLICT);
        }
        surveyFormRepository.save(surveyForm);
        return ResponseEntity.ok(EntityModel.of(surveyForm));
    }

    @GetMapping("/formName/{surveyName}")
    public ResponseEntity<EntityModel<SurveyForm>> getSurveyForm(@PathVariable("surveyName") String surveyName) throws JsonProcessingException {
        Optional<SurveyForm> isSurveyPresent = surveyFormRepository.findById(surveyName);
        if (isSurveyPresent.isPresent()) {
            SurveyForm survey = surveyFormRepository.findById(surveyName).get();
            return ResponseEntity.ok(EntityModel.of(survey));
        }
        responseHeaders.set("Message", "Element with this name doesn't exist");
        return new ResponseEntity(responseHeaders, HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<SurveyForm>> getAllSurveyForm(@RequestParam(required = false) String surveyName) {
        List<SurveyForm> surveyList = (List<SurveyForm>) surveyFormRepository.findAll();
        if (surveyList.isEmpty()) {
            responseHeaders.set("Message", "No element is present in the database");
            return new ResponseEntity(responseHeaders, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(CollectionModel.of(surveyList));
    }

    @PutMapping("/formName/{surveyName}")
    public ResponseEntity<EntityModel<SurveyForm>> updateSurveyForm(@PathVariable("surveyName") String surveyName, @Valid @RequestBody SurveyForm surveyForm) throws JsonProcessingException {
        Optional<SurveyForm> isSurveyPresent = surveyFormRepository.findById(surveyName);
        if (isSurveyPresent.isPresent()) {
            SurveyForm survey = isSurveyPresent.get();

            if (survey.getSurveyName().equals(surveyForm.getSurveyName())) {

                survey.setSurveyName(surveyForm.getSurveyName());
                survey.setContractMetaData(surveyForm.getContractMetaData());
                survey.setServiceSchema(surveyForm.getServiceSchema());
                survey.setLastUpdatedBy(surveyForm.getLastUpdatedBy());

                final SurveyForm updatedSurvey = surveyFormRepository.save(survey);
                return ResponseEntity.ok(EntityModel.of(updatedSurvey));
            }
            responseHeaders.set("Message", "Cannot change the id field(survey name)");
            return new ResponseEntity(responseHeaders, HttpStatus.NO_CONTENT);
        }
        responseHeaders.set("Message", "Element with this name doesn't exist");
        return new ResponseEntity(responseHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/formName/{surveyName}")
    public ResponseEntity<EntityModel<SurveyForm>> deleteSurveyForm(@PathVariable("surveyName") String surveyName) {
        Optional<SurveyForm> isSurveyPresent = surveyFormRepository.findById(surveyName);
        if (isSurveyPresent.isPresent()) {
            surveyFormRepository.deleteById(surveyName);
            responseHeaders.set("Message", "Element with the given name is deleted");
            return new ResponseEntity(responseHeaders, HttpStatus.OK);
        }
        responseHeaders.set("Message", "Element with this name doesn't exist");
        return new ResponseEntity(responseHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<EntityModel<SurveyForm>> deleteAllSurveyForm() {
        List<SurveyForm> surveyFormList = (List<SurveyForm>) surveyFormRepository.findAll();
        if (surveyFormList.isEmpty()) {
            responseHeaders.set("Message", "No element is present in the database");
            return new ResponseEntity(responseHeaders, HttpStatus.NO_CONTENT);
        }
        surveyFormRepository.deleteAll();
        responseHeaders.set("Message", "All the elements are deleted");
        return new ResponseEntity(responseHeaders, HttpStatus.OK);
    }
}