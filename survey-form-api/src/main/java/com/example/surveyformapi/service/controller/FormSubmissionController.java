package com.example.surveyformapi.service.controller;
import com.example.surveyformapi.service.model.CompositeID;
import com.example.surveyformapi.service.model.FormData;
import com.example.surveyformapi.service.repository.FormDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/formData")
public class FormSubmissionController {
    private final FormDataRepository formDataRepository;
    HttpHeaders responseHeaders = new HttpHeaders();

    public FormSubmissionController(FormDataRepository formDataRepository) {
        this.formDataRepository = formDataRepository;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<FormData>> createFormData(@Valid @RequestBody FormData formData) throws JsonProcessingException {
        log.info("Request received from UI" + formData);

        String userId = formData.getUserId();
        String formName = formData.getFormName();
        Optional<FormData> isFormDataPresent = formDataRepository.findById(new CompositeID(formName, userId));
        if (isFormDataPresent.isPresent()) {
            responseHeaders.set("Message", "Entry with the same ID already exists, please try again");
            responseHeaders.set("Access-Control-Expose-Headers","Message");

            return new ResponseEntity(responseHeaders, HttpStatus.CONFLICT);
        }
        formDataRepository.save(formData);
        return ResponseEntity.ok(EntityModel.of(formData));
    }
}
